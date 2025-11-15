// Global JavaScript for SmartRoom

$(document).ready(function () {
	// Initialize tooltips
	$('[data-toggle="tooltip"]').tooltip();

	// Initialize popovers
	$('[data-toggle="popover"]').popover();

	// Confirm delete action
	$('.btn-delete').on('click', function (e) {
		if (!confirm('Bạn chắc chắn muốn xóa?')) {
			e.preventDefault();
			return false;
		}
	});

	// Auto-hide alerts after 5 seconds
	$('.alert:not(.alert-permanent)')
		.delay(5000)
		.fadeOut('slow', function () {
			$(this).remove();
		});

	// Initialize the sidebar tree
	$('.nav-sidebar').on('click', '.nav-link', function () {
		if ($(this).attr('href') === '#') {
			$(this).siblings('.nav-treeview').slideToggle('fast');
			$(this).toggleClass('menu-open');
			$(this).find('.right').toggleClass('fa-angle-left fa-angle-down');
		}
	});

	// AJAX Form Submit Handler
	$(document).on('submit', 'form[data-ajax="true"]', function (e) {
		e.preventDefault();

		const $form = $(this);
		const $btn = $form.find('button[type="submit"]');

		$.ajax({
			url: $form.attr('action'),
			type: $form.attr('method'),
			data: $form.serialize(),
			beforeSend: function () {
				$btn.prop('disabled', true).append(' <span class="spinner-border spinner-border-sm ml-2"></span>');
			},
			success: function (response) {
				showAlert('Thành công!', 'success');
				setTimeout(() => {
					if ($form.data('redirect')) {
						window.location.href = $form.data('redirect');
					}
				}, 1500);
			},
			error: function (error) {
				const message = error.responseJSON?.message || 'Có lỗi xảy ra!';
				showAlert(message, 'danger');
			},
			complete: function () {
				$btn.prop('disabled', false).find('.spinner-border').remove();
			},
		});
	});
});

/**
 * Show alert message
 */
function showAlert(message, type = 'info') {
	const alertHtml = `
        <div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    `;

	$('.content-header').after(alertHtml);

	// Auto-hide after 5 seconds
	setTimeout(() => {
		$('.alert:not(.alert-permanent)').fadeOut('slow', function () {
			$(this).remove();
		});
	}, 5000);
}

/**
 * API Call Helper
 */
function apiCall(method, endpoint, data = null, callback = null) {
	const settings = {
		url: `/api/v1${endpoint}`,
		type: method.toUpperCase(),
		contentType: 'application/json',
		success: function (response) {
			if (callback) {
				callback(response);
			}
		},
		error: function (error) {
			const message = error.responseJSON?.message || 'Có lỗi xảy ra!';
			showAlert(message, 'danger');
		},
	};

	if (data) {
		settings.data = JSON.stringify(data);
	}

	return $.ajax(settings);
}
