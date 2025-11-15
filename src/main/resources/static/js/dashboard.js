// ============================================
// DASHBOARD APPLICATION - CLIENT SIDE
// Tích hợp với Spring Boot REST API
// ============================================

(function () {
	'use strict';

	// ============================================
	// CONFIGURATION
	// ============================================
	const CONFIG = {
		CHART_COLORS: ['#4bc0c0', '#ff6384', '#36a2eb', '#ffce56', '#9966ff', '#ff9f40'],
		DATE_FORMAT: 'YYYY/MM/DD HH:mm',
		TIME_FORMAT: 'yyyy-MM-dd HH:mm',
	};

	// Global variables from server (passed via Thymeleaf)
	const LANG_CODE = window.DASHBOARD_CONFIG?.langCode || 'vi';

	// ============================================
	// UTILITY FUNCTIONS
	// ============================================
	class ApiClient {
		/**
		 * Fetch devices from existing API
		 */
		static async fetchDevices() {
			try {
				const response = await fetch(`/api/v1/devices?lang=${LANG_CODE}&size=1000`);
				if (!response.ok) throw new Error('Failed to fetch devices');
				const result = await response.json();
				return result.data.items; // Extract items from PaginationInfo
			} catch (error) {
				console.error('Error fetching devices:', error);
				throw error;
			}
		}

		/**
		 * Fetch sensor readings for multiple sensors within time range
		 * Uses existing /api/v1/sensor-readings/by-sensors endpoint
		 * @param {Array} sensorIds - Array of sensor IDs
		 * @param {string} startTime - ISO 8601 timestamp
		 * @param {string} endTime - ISO 8601 timestamp
		 */
		static async fetchSensorReadings(sensorIds, startTime, endTime) {
			try {
				const response = await fetch(`/api/v1/sensor-readings/by-sensors`, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
					},
					body: JSON.stringify({
						sensorIds: sensorIds,
						startTime: startTime,
						endTime: endTime,
						page: 0,
						size: 10000,
					}),
				});

				if (!response.ok) {
					const errorText = await response.text();
					console.error('API Error Response:', errorText);
					throw new Error(`Failed to fetch sensor readings: ${response.status} ${response.statusText}`);
				}

				const result = await response.json();
				console.log('Full API Response:', result);

				if (result.status && (result.status < 200 || result.status >= 300)) {
					throw new Error(result.message || 'API returned error status');
				}

				// Extract items from PaginationInfo (data.items)
				if (result.data && result.data.items) {
					console.log('Returning data.items with', result.data.items.length, 'items');
					return result.data.items;
				}

				// If no nested structure, return data directly
				if (Array.isArray(result.data)) {
					console.log('Returning data array with', result.data.length, 'items');
					return result.data;
				}

				console.warn('Unexpected API response structure:', result);
				return [];
			} catch (error) {
				console.error('Error fetching sensor readings:', error);
				throw error;
			}
		}
	}

	// ============================================
	// CHART PLUGIN
	// ============================================
	class ChartPlugin {
		static peakHighlighter = {
			id: 'peakHighlighterPlugin',
			// afterDraw: (chart) => {
			// 	const ctx = chart.ctx;

			// 	chart.data.datasets.forEach((dataset, i) => {
			// 		const meta = chart.getDatasetMeta(i);
			// 		if (!meta.hidden && dataset.data.length > 0) {
			// 			// Find min and max values with their indices
			// 			let maxVal = -Infinity;
			// 			let minVal = Infinity;
			// 			let maxIndex = -1;
			// 			let minIndex = -1;

			// 			dataset.data.forEach((point, idx) => {
			// 				const value = point.y;
			// 				if (value > maxVal) {
			// 					maxVal = value;
			// 					maxIndex = idx;
			// 				}
			// 				if (value < minVal) {
			// 					minVal = value;
			// 					minIndex = idx;
			// 				}
			// 			});

			// 			// // Draw max point
			// 			// if (maxIndex >= 0 && maxIndex < meta.data.length) {
			// 			// 	const point = meta.data[maxIndex];
			// 			// 	ctx.save();
			// 			// 	ctx.fillStyle = 'red';
			// 			// 	ctx.beginPath();
			// 			// 	ctx.arc(point.x, point.y, 5, 0, 2 * Math.PI);
			// 			// 	ctx.fill();
			// 			// 	ctx.restore();
			// 			// }

			// 			// // Draw min point
			// 			// if (minIndex >= 0 && minIndex < meta.data.length) {
			// 			// 	const point = meta.data[minIndex];
			// 			// 	ctx.save();
			// 			// 	ctx.fillStyle = 'blue';
			// 			// 	ctx.beginPath();
			// 			// 	ctx.arc(point.x, point.y, 5, 0, 2 * Math.PI);
			// 			// 	ctx.fill();
			// 			// 	ctx.restore();
			// 			// }
			// 		}
			// 	});
			// },
		};
	}

	// ============================================
	// CHART MANAGER
	// ============================================
	class ChartManager {
		constructor() {
			this.charts = {};
			this.chartsList = [];
		}

		createChart(canvasId, yAxisLabel) {
			const ctx = document.getElementById(canvasId)?.getContext('2d');
			if (!ctx) {
				console.error(`Canvas with id ${canvasId} not found`);
				return null;
			}

			const chart = new Chart(ctx, {
				type: 'line',
				data: { datasets: [] },
				options: this._getChartOptions(yAxisLabel),
				plugins: [ChartPlugin.peakHighlighter],
			});

			this.charts[canvasId] = chart;
			this.chartsList.push(chart);
			return chart;
		}

		_getChartOptions(yAxisLabel) {
			return {
				responsive: true,
				maintainAspectRatio: false,
				animation: false,
				parsing: false,
				interaction: {
					mode: 'index',
					intersect: false,
					onHover: (e, elements, chart) => this.syncHover(chart),
				},
				plugins: {
					tooltip: {
						enabled: true,
						backgroundColor: 'rgba(0, 0, 0, 0.8)',
					},
					legend: { position: 'bottom' },
					peakHighlighterPlugin: {},
				},
				scales: {
					x: {
						type: 'time',
						time: {
							unit: 'hour',
							displayFormats: { hour: 'HH:mm', day: 'MMM dd' },
							tooltipFormat: CONFIG.TIME_FORMAT,
						},
						title: { display: true, text: 'Timestamp (Recorded At)' },
						ticks: { display: true },
					},
					y: {
						type: 'linear',
						position: 'left',
						title: { display: true, text: yAxisLabel },
					},
				},
			};
		}

		syncHover(sourceChart) {
			const tooltip = sourceChart.tooltip;

			if (!tooltip || !tooltip.getActiveElements || !tooltip.getActiveElements().length) {
				this.chartsList.forEach((chart) => {
					if (chart !== sourceChart) {
						chart.tooltip.setActiveElements([], { x: 0, y: 0 });
						chart.update('none');
					}
				});
				return;
			}

			const activeElements = tooltip.getActiveElements();
			const activeIndex = activeElements[0].index;

			this.chartsList.forEach((chart) => {
				if (chart !== sourceChart) {
					const activeItems = chart.data.datasets.map((dataset, datasetIndex) => ({
						datasetIndex: datasetIndex,
						index: activeIndex,
					}));
					chart.tooltip.setActiveElements(activeItems, { x: 0, y: 0 });
					chart.update('none');
				}
			});
		}

		clearAllCharts() {
			this.chartsList.forEach((chart) => {
				chart.data.datasets = [];
				chart.update();
			});
		}

		updateAllCharts(minTime, maxTime) {
			this.chartsList.forEach((chart) => {
				if (chart.scales && chart.scales.x) {
					chart.scales.x.options.min = minTime;
					chart.scales.x.options.max = maxTime;
					chart.update();
				}
			});
		}

		destroy() {
			this.chartsList.forEach((chart) => {
				chart.destroy();
			});
			this.charts = {};
			this.chartsList = [];
		}
	}

	// ============================================
	// TABLE MANAGER
	// ============================================
	class DeviceTableManager {
		constructor(selector, onSelectionChange) {
			this.selector = selector;
			this.onSelectionChange = onSelectionChange;
			this.dataTable = null;
			this.selectedDevices = [];
		}

		initialize() {
			// Initialize DataTable on existing table
			this.dataTable = $(this.selector).DataTable({
				pageLength: 10,
				lengthChange: false,
				ordering: true,
				autoWidth: false,
				info: true,
				searching: true,
			});

			this._attachEventHandlers();
		}

		_attachEventHandlers() {
			$('#select-all').on('click', (e) => this._handleSelectAll(e));

			$(`${this.selector} tbody`).on('click', 'input.row-checkbox', (e) => {
				this._handleRowCheckbox(e);
			});

			this.dataTable.on('draw', () => this._handleTableDraw());
		}

		_handleSelectAll(e) {
			const isChecked = e.target.checked;
			const rows = this.dataTable.rows({ page: 'current' }).nodes();

			$('input.row-checkbox', rows).prop('checked', isChecked);
			$(rows).each((_, row) => {
				const deviceId = parseInt($(row).data('device-id'));
				const rowData = this._getDeviceDataById(deviceId);
				if (rowData) {
					this._updateSelection(rowData, isChecked);
				}
			});
		}

		_handleRowCheckbox(e) {
			const isChecked = e.target.checked;
			const tr = $(e.target).closest('tr');
			const deviceId = parseInt(tr.data('device-id'));
			const rowData = this._getDeviceDataById(deviceId);

			if (rowData) {
				this._updateSelection(rowData, isChecked);
				this._updateSelectAllCheckbox();
			}
		}

		_handleTableDraw() {
			const rows = this.dataTable.rows({ page: 'current' }).nodes();
			let totalOnPage = 0;
			let checkedOnPage = 0;

			$(rows).each((_, row) => {
				const deviceId = parseInt($(row).data('device-id'));
				const checkbox = $(row).find('input.row-checkbox');
				totalOnPage++;

				// Check if this device is in selected list
				const isSelected = this.selectedDevices.some((d) => d.id === deviceId);
				checkbox.prop('checked', isSelected);
				if (isSelected) checkedOnPage++;
			});

			$('#select-all').prop('checked', totalOnPage > 0 && totalOnPage === checkedOnPage);
		}

		_updateSelection(rowData, isChecked) {
			const index = this.selectedDevices.findIndex((item) => item.id === rowData.id);

			if (isChecked && index === -1) {
				this.selectedDevices.push(rowData);
			} else if (!isChecked && index !== -1) {
				this.selectedDevices.splice(index, 1);
			}

			if (this.onSelectionChange) {
				this.onSelectionChange(this.selectedDevices);
			}
		}

		_updateSelectAllCheckbox() {
			const rows = this.dataTable.rows({ page: 'current' }).nodes();
			const total = $('input.row-checkbox', rows).length;
			const checked = $('input.row-checkbox:checked', rows).length;

			$('#select-all').prop('checked', total > 0 && total === checked);
		}

		_getDeviceDataById(deviceId) {
			// Get device data from global config (passed from server)
			if (!window.DASHBOARD_CONFIG || !Array.isArray(window.DASHBOARD_CONFIG.devices)) {
				console.error('DASHBOARD_CONFIG or devices array is not available');
				return null;
			}
			return window.DASHBOARD_CONFIG.devices.find((d) => d && d.id === deviceId);
		}

		getSelectedDevices() {
			return this.selectedDevices;
		}
	}

	// ============================================
	// DATE RANGE FILTER
	// ============================================
	class DateRangeFilter {
		constructor(selector, onApply) {
			this.selector = selector;
			this.onApply = onApply;
			this.picker = null;
		}

		initialize(startDate, endDate) {
			const $input = $(this.selector);

			this.picker = $input.daterangepicker(
				{
					startDate: moment(startDate),
					endDate: moment(endDate),
					timePicker: true,
					timePicker24Hour: true,
					timePickerIncrement: 30,
					locale: {
						format: CONFIG.DATE_FORMAT,
						applyLabel: 'Áp dụng',
						cancelLabel: 'Hủy',
						fromLabel: 'Từ',
						toLabel: 'Đến',
						customRangeLabel: 'Tùy chỉnh',
					},
					ranges: {
						'Hôm nay': [moment().startOf('day'), moment().endOf('day')],
						'Hôm qua': [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')],
						'7 ngày qua': [moment().subtract(6, 'days').startOf('day'), moment().endOf('day')],
						'30 ngày qua': [moment().subtract(29, 'days').startOf('day'), moment().endOf('day')],
						'Tháng này': [moment().startOf('month'), moment().endOf('month')],
						'Tháng trước': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
					},
				},
				(start, end) => {
					if (this.onApply) {
						this.onApply(start.toDate(), end.toDate());
					}
				},
			);

			// Set initial display
			$input.val(`${moment(startDate).format(CONFIG.DATE_FORMAT)} - ${moment(endDate).format(CONFIG.DATE_FORMAT)}`);
		}

		setRange(startDate, endDate) {
			if (this.picker) {
				$(this.selector).data('daterangepicker').setStartDate(moment(startDate));
				$(this.selector).data('daterangepicker').setEndDate(moment(endDate));
			}
		}
	}

	// ============================================
	// MAIN DASHBOARD APPLICATION
	// ============================================
	class DashboardApp {
		constructor() {
			this.chartManager = new ChartManager();
			this.tableManager = null;
			this.dateRangeFilter = null;
			this.selectedDevices = [];
			this.currentTimeRange = {
				start: null,
				end: null,
			};
		}

		initialize() {
			console.log('Initializing Dashboard Application...');

			// Initialize table
			this.tableManager = new DeviceTableManager('#deviceTable', (devices) => {
				this.selectedDevices = devices;
				$('#selected-count-span').text(`Số thiết bị đã chọn: ${devices.length}`);
			});
			this.tableManager.initialize();

			// Initialize date range
			const defaultStart = window.DASHBOARD_CONFIG?.defaultStartTime || new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toISOString();
			const defaultEnd = window.DASHBOARD_CONFIG?.defaultEndTime || new Date().toISOString();

			this.currentTimeRange.start = new Date(defaultStart);
			this.currentTimeRange.end = new Date(defaultEnd);

			this.dateRangeFilter = new DateRangeFilter('#date-filter-input', (start, end) => {
				this.currentTimeRange.start = start;
				this.currentTimeRange.end = end;
				this.drawCharts();
			});

			this.dateRangeFilter.initialize(this.currentTimeRange.start, this.currentTimeRange.end);

			// Initialize charts (but don't populate yet)
			this.chartManager.createChart('sensorChartTemp', 'Temperature (°C)');
			this.chartManager.createChart('sensorChartWatt', 'Power (Watt)');
			this.chartManager.createChart('sensorChartVolt', 'Voltage (Volt)');
			this.chartManager.createChart('sensorChartAmpe', 'Current (Ampe)');

			// Attach button event
			$('#show-chart-btn').on('click', () => this.onShowChartClick());

			console.log('Dashboard Application initialized successfully');
		}

		async onShowChartClick() {
			if (this.selectedDevices.length === 0) {
				alert('Vui lòng chọn ít nhất một thiết bị để vẽ biểu đồ');
				return;
			}

			// Show loading state
			$('#show-chart-btn').prop('disabled', true).html('<span class="spinner-border spinner-border-sm me-2"></span>Đang tải...');

			try {
				await this.drawCharts();
				// Show chart section
				$('#chart-analysis-section').slideDown();
			} catch (error) {
				console.error('Error drawing charts:', error);
				alert('Có lỗi xảy ra khi vẽ biểu đồ. Vui lòng thử lại.');
			} finally {
				// Restore button state
				$('#show-chart-btn').prop('disabled', false).html('<i class="bi bi-graph-up me-2"></i>Vẽ Biểu Đồ');
			}
		}

		async drawCharts() {
			console.log('Drawing charts for selected devices:', this.selectedDevices);

			// Clear existing charts
			this.chartManager.clearAllCharts();

			// Get all sensor IDs from selected devices
			const sensorIds = [];
			const deviceSensorMap = {}; // Map sensor ID to device info

			this.selectedDevices.forEach((device) => {
				// Check if device and sensors exist and sensors is an array
				if (device && Array.isArray(device.sensors) && device.sensors.length > 0) {
					device.sensors.forEach((sensor) => {
						sensorIds.push(sensor.id);
						deviceSensorMap[sensor.id] = {
							deviceName: device.name,
							sensorName: sensor.name,
							sensorType: sensor.type,
						};
					});
				}
			});

			if (sensorIds.length === 0) {
				console.warn('Selected devices have no sensors:', this.selectedDevices);
				alert('Các thiết bị đã chọn không có cảm biến nào hoặc dữ liệu cảm biến chưa được tải');
				return;
			}

			console.log('Total sensor IDs to query:', sensorIds.length, sensorIds);

			const startTime = this.currentTimeRange.start.toISOString();
			const endTime = this.currentTimeRange.end.toISOString();

			try {
				// Fetch sensor readings using existing API
				const sensorReadings = await ApiClient.fetchSensorReadings(sensorIds, startTime, endTime);

				console.log('Received sensor readings:', sensorReadings);

				if (!Array.isArray(sensorReadings) || sensorReadings.length === 0) {
					// alert('Không có dữ liệu cảm biến trong khoảng thời gian đã chọn');
					return;
				}

				// Group readings by sensor ID
				const readingsBySensor = {};
				sensorReadings.forEach((reading) => {
					if (!readingsBySensor[reading.sensorId]) {
						readingsBySensor[reading.sensorId] = [];
					}
					readingsBySensor[reading.sensorId].push(reading);
				});

				// Process data for each sensor
				let colorIndex = 0;
				Object.keys(readingsBySensor).forEach((sensorId) => {
					const readings = readingsBySensor[sensorId];
					const sensorInfo = deviceSensorMap[sensorId];

					if (sensorInfo && Array.isArray(readings) && readings.length > 0) {
						const color = CONFIG.CHART_COLORS[colorIndex % CONFIG.CHART_COLORS.length];
						const label = `${sensorInfo.deviceName} - ${sensorInfo.sensorName}`;

						// Add to appropriate charts based on available data
						this._addTemperatureDataset(label, readings, color);
						this._addElectricityDatasets(label, readings, color);

						colorIndex++;
					}
				});

				// Update all charts
				this.chartManager.chartsList.forEach((chart) => chart.update());
			} catch (error) {
				console.error('Error in drawCharts:', error);
				throw error;
			}
		}

		_addTemperatureDataset(label, readings, color) {
			if (!Array.isArray(readings) || readings.length === 0) {
				return;
			}

			const tempData = readings
				.filter((r) => r && r.tempC !== null && r.tempC !== undefined && r.timestamp)
				.map((r) => ({
					x: new Date(r.timestamp).getTime(),
					y: r.tempC,
				}));

			if (tempData.length > 0) {
				const chart = this.chartManager.charts['sensorChartTemp'];
				if (chart) {
					chart.data.datasets.push({
						label: label,
						data: tempData,
						borderColor: color,
						backgroundColor: color + '20',
						borderWidth: 2,
						pointRadius: 0,
						tension: 0.1,
					});
				}
			}
		}

		_addElectricityDatasets(label, readings, color) {
			if (!Array.isArray(readings) || readings.length === 0) {
				return;
			}

			// Watt
			const wattData = readings
				.filter((r) => r && r.watt !== null && r.watt !== undefined && r.timestamp)
				.map((r) => ({
					x: new Date(r.timestamp).getTime(),
					y: r.watt,
				}));

			if (wattData.length > 0) {
				const chartWatt = this.chartManager.charts['sensorChartWatt'];
				if (chartWatt) {
					chartWatt.data.datasets.push({
						label: label,
						data: wattData,
						borderColor: color,
						backgroundColor: color + '20',
						borderWidth: 2,
						pointRadius: 0,
						tension: 0.1,
					});
				}
			}

			// Volt
			const voltData = readings
				.filter((r) => r && r.volt !== null && r.volt !== undefined && r.timestamp)
				.map((r) => ({
					x: new Date(r.timestamp).getTime(),
					y: r.volt,
				}));

			if (voltData.length > 0) {
				const chartVolt = this.chartManager.charts['sensorChartVolt'];
				if (chartVolt) {
					chartVolt.data.datasets.push({
						label: label,
						data: voltData,
						borderColor: color,
						backgroundColor: color + '20',
						borderWidth: 2,
						pointRadius: 0,
						tension: 0.1,
					});
				}
			}

			// Ampe
			const ampeData = readings
				.filter((r) => r && r.ampe !== null && r.ampe !== undefined && r.timestamp)
				.map((r) => ({
					x: new Date(r.timestamp).getTime(),
					y: r.ampe,
				}));

			if (ampeData.length > 0) {
				const chartAmpe = this.chartManager.charts['sensorChartAmpe'];
				if (chartAmpe) {
					chartAmpe.data.datasets.push({
						label: label,
						data: ampeData,
						borderColor: color,
						backgroundColor: color + '20',
						borderWidth: 2,
						pointRadius: 0,
						tension: 0.1,
					});
				}
			}
		}
	}

	// ============================================
	// APPLICATION ENTRY POINT
	// ============================================
	$(function () {
		const app = new DashboardApp();
		app.initialize();
	});
})();
