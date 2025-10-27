-- -----------------------------------------------------
-- Script Mock Data SIÊU CẤP GIÀU cho hệ thống Smart Room IoT
-- -----------------------------------------------------
USE smart_room_iot;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE sensor_data;
TRUNCATE TABLE sensor_data_type;
TRUNCATE TABLE sensor;
TRUNCATE TABLE device;
TRUNCATE TABLE hub;
TRUNCATE TABLE room;
TRUNCATE TABLE data_type;
TRUNCATE TABLE connection_type;
SET FOREIGN_KEY_CHECKS = 1;

-- -----------------------------------------------------
-- Bảng 2: connection_type (Không đổi)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `connection_type` (`code`, `name`, `description`, `v`)
VALUES
    ('ONE_WIRE', '1-Wire', 'Giao thức giao tiếp bus 1 dây', 1),
    ('I2C', 'I2C (Inter-Integrated Circuit)', 'Giao thức giao tiếp nối tiếp', 1),
    ('UART', 'UART (Serial)', 'Giao thức truyền thông nối tiếp không đồng bộ', 1),
    ('BLE', 'Bluetooth Low Energy (BLE)', 'Chuẩn Bluetooth tiết kiệm năng lượng', 1),
    ('WIFI', 'Wi-Fi (802.11)', 'Chuẩn kết nối mạng không dây', 1),
    ('Z_WAVE', 'Z-Wave', 'Chuẩn kết nối không dây cho nhà thông minh', 1),
    ('LORA', 'LoRa / LoRaWAN', 'Chuẩn kết nối không dây tầm xa, công suất thấp', 1),
    ('ZIGBEE', 'Zigbee', 'Chuẩn kết nối không dây (thường dùng cho mesh network)', 1)
ON DUPLICATE KEY UPDATE `name` = `name`; -- Chống lỗi nếu chạy lại
COMMIT;

-- Lưu ID (BIGINT) của các connection_type vào biến
SET @conn_wifi = (SELECT id FROM connection_type WHERE code = 'WIFI');
SET @conn_ble = (SELECT id FROM connection_type WHERE code = 'BLE');
SET @conn_zigbee = (SELECT id FROM connection_type WHERE code = 'ZIGBEE');
SET @conn_i2c = (SELECT id FROM connection_type WHERE code = 'I2C');
SET @conn_zwave = (SELECT id FROM connection_type WHERE code = 'Z_WAVE');

-- -----------------------------------------------------
-- Bảng 3: data_type (Bổ sung)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `data_type` (`code`, `name`, `unit`, `description`, `v`)
VALUES
    ('TEM', 'Nhiệt độ', '°C', 'Đo nhiệt độ môi trường', 1),
    ('HUM', 'Độ ẩm', '%', 'Đo độ ẩm tương đối của không khí', 1),
    ('LIGHT_LEVEL', 'Cường độ ánh sáng', 'lux', 'Đo cường độ ánh sáng xung quanh', 1),
    ('POWER_USAGE', 'Công suất tiêu thụ', 'W', 'Đo công suất điện tiêu thụ tức thời', 1),
    ('ENERGY_TOTAL', 'Tổng điện năng', 'kWh', 'Đo tổng điện năng tiêu thụ (số điện)', 1),
    ('VOL', 'Điện áp', 'V', 'Đo điện áp dòng điện', 1),
    ('PM25', 'Bụi mịn PM2.5', 'µg/m³', 'Đo nồng độ bụi mịn PM2.5', 1),
    ('AQI', 'Chỉ số chất lượng không khí', 'AQI', 'Đo chỉ số AQI tổng hợp', 1),
    ('MOTION', 'Chuyển động', 'binary', 'Phát hiện chuyển động (1=có, 0=không)', 1),
    ('DOOR_STATE', 'Trạng thái cửa', 'binary', 'Trạng thái cửa (1=mở, 0=đóng)', 1)
ON DUPLICATE KEY UPDATE `name` = `name`; -- Chống lỗi nếu chạy lại
COMMIT;

-- Lưu ID (BIGINT) của các data_type vào biến
SET @data_temp = (SELECT id FROM data_type WHERE code = 'TEM');
SET @data_humid = (SELECT id FROM data_type WHERE code = 'HUM');
SET @data_light = (SELECT id FROM data_type WHERE code = 'LIGHT_LEVEL');
SET @data_power = (SELECT id FROM data_type WHERE code = 'POWER_USAGE');
SET @data_energy = (SELECT id FROM data_type WHERE code = 'ENERGY_TOTAL');
SET @data_volt = (SELECT id FROM data_type WHERE code = 'VOL');
SET @data_pm25 = (SELECT id FROM data_type WHERE code = 'PM25');
SET @data_aqi = (SELECT id FROM data_type WHERE code = 'AQI');
SET @data_motion = (SELECT id FROM data_type WHERE code = 'MOTION');
SET @data_door = (SELECT id FROM data_type WHERE code = 'DOOR_STATE');


-- -----------------------------------------------------
-- Bảng 1: room (Giữ nguyên)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `room` (`id`, `name`, `location`, `description`, `v`)
VALUES
    ('R_HA801', 'Phòng học H.A8.01', 'Tòa nhà H, Lầu 8, Phòng 01', 'Phòng học lý thuyết khoa CNTT, có 1 máy lạnh, 8 đèn.', 1),
    ('R_XA505', 'Phòng thực hành X.A5.05', 'Tòa nhà X, Lầu 5, Phòng 05', 'Phòng thực hành máy tính, 60 máy trạm, 2 máy lạnh.', 1),
    ('R_VPCNTT', 'Văn phòng Khoa CNTT', 'Tòa nhà H, Lầu 7, Khu A', 'Văn phòng làm việc của Giáo vụ và Giảng viên.', 1)
ON DUPLICATE KEY UPDATE `name` = `name`; -- Chống lỗi nếu chạy lại
COMMIT;

-- Lưu ID (VARCHAR) của các room
SET @room_ha801 = 'R_HA801';
SET @room_xa505 = 'R_XA505';
SET @room_vp_cntt = 'R_VPCNTT';


-- -----------------------------------------------------
-- Bảng 4: hub (Tăng số lượng)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `hub` (`id`, `name`, `status`, `location`, `description`, `room_id`, `v`)
VALUES
    ('HUB_00:1A:2B:3C:4D:01', 'Hub trung tâm H.A8.01', 'online', 'Góc phòng, gần cửa ra vào', 'Hub Gateway chính cho phòng H.A8.01', @room_ha801, 1),
    ('HUB_00:1A:2B:3C:4D:02', 'Hub trung tâm X.A5.05', 'online', 'Trên bàn giáo viên', 'Hub Gateway chính cho phòng X.A5.05', @room_xa505, 1),
    ('HUB_00:1A:2B:3C:4D:03', 'Hub Văn phòng Khoa', 'offline', 'Phòng server nhỏ', 'Hub Gateway cho văn phòng khoa', @room_vp_cntt, 1),
    ('HUB_00:1A:2B:3C:4D:04', 'Hub Mesh 1 H.A8.01', 'online', 'Góc phòng, gần cửa sổ', 'Hub mesh (Zigbee/Z-Wave) cho H.A8.01', @room_ha801, 1),
    ('HUB_00:1A:2B:3C:4D:05', 'Hub Mesh 1 X.A5.05', 'error', 'Góc phòng, gần máy lạnh 1', 'Hub mesh cho phòng X.A5.05', @room_xa505, 1),
    ('HUB_00:1A:2B:3C:4D:06', 'Hub dự phòng Văn phòng Khoa', 'online', 'Phòng họp nhỏ', 'Hub dự phòng cho văn phòng khoa', @room_vp_cntt, 1),
    ('HUB_00:1A:2B:3C:4D:07', 'Hub Mesh 2 H.A8.01', 'online', 'Giữa trần phòng', 'Hub mesh (BLE) cho H.A8.01', @room_ha801, 1),
    ('HUB_00:1A:2B:3C:4D:08', 'Hub Mesh 2 X.A5.05', 'online', 'Góc phòng, gần máy lạnh 2', 'Hub mesh cho phòng X.A5.05', @room_xa505, 1)
ON DUPLICATE KEY UPDATE `name` = `name`;
COMMIT;

-- Lưu ID (VARCHAR) của các hub
SET @hub_ha801_main = 'HUB_00:1A:2B:3C:4D:01';
SET @hub_ha801_mesh1 = 'HUB_00:1A:2B:3C:4D:04';
SET @hub_ha801_mesh2 = 'HUB_00:1A:2B:3C:4D:07';
SET @hub_xa505_main = 'HUB_00:1A:2B:3C:4D:02';
SET @hub_xa505_mesh1 = 'HUB_00:1A:2B:3C:4D:05';
SET @hub_xa505_mesh2 = 'HUB_00:1A:2B:3C:4D:08';
SET @hub_vp_cntt_main = 'HUB_00:1A:2B:3C:4D:03';
SET @hub_vp_cntt_backup = 'HUB_00:1A:2B:3C:4D:06';


-- -----------------------------------------------------
-- Bảng 5: device (Tăng CỰC KỲ GIÀU)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `device` (`id`, `name`, `status`, `location`, `hub_id`, `connection_type_id`, `v`)
VALUES
    -- ===============================================
    -- Thiết bị cho phòng H.A8.01 (20 thiết bị)
    -- ===============================================
    ('DEV_H801_AC01', 'Máy lạnh Daikin H.A8.01', 'online', 'Treo tường, bên trái bảng', @hub_ha801_main, @conn_wifi, 1),
    ('DEV_H801_PROJ01', 'Máy chiếu Epson H.A8.01', 'online', 'Treo trần, giữa phòng', @hub_ha801_main, @conn_wifi, 1),
    ('DEV_H801_AIRPUR01', 'Máy lọc không khí Sharp H.A8.01', 'online', 'Góc phòng, gần cửa sổ', @hub_ha801_main, @conn_wifi, 1),
    ('DEV_H801_CAM01', 'Camera giám sát H.A8.01', 'online', 'Góc phòng, gần cửa ra vào', @hub_ha801_main, @conn_wifi, 1),
    
    -- 8 Đèn (Zigbee)
    ('DEV_H801_L01', 'Đèn 1 (Dãy A) H.A8.01', 'online', 'Trần nhà, dãy A, vị trí 1', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L02', 'Đèn 2 (Dãy A) H.A8.01', 'online', 'Trần nhà, dãy A, vị trí 2', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L03', 'Đèn 3 (Dãy A) H.A8.01', 'offline', 'Trần nhà, dãy A, vị trí 3', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L04', 'Đèn 4 (Dãy A) H.A8.01', 'online', 'Trần nhà, dãy A, vị trí 4', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L05', 'Đèn 5 (Dãy B) H.A8.01', 'online', 'Trần nhà, dãy B, vị trí 1', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L06', 'Đèn 6 (Dãy B) H.A8.01', 'online', 'Trần nhà, dãy B, vị trí 2', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L07', 'Đèn 7 (Dãy B) H.A8.01', 'online', 'Trần nhà, dãy B, vị trí 3', @hub_ha801_mesh1, @conn_zigbee, 1),
    ('DEV_H801_L08', 'Đèn 8 (Dãy B) H.A8.01', 'online', 'Trần nhà, dãy B, vị trí 4', @hub_ha801_mesh1, @conn_zigbee, 1),

    -- 5 Ổ cắm (Z-Wave)
    ('DEV_H801_PLUG01', 'Ổ cắm (Bàn GV)', 'online', 'Bàn giáo viên', @hub_ha801_mesh1, @conn_zwave, 1),
    ('DEV_H801_PLUG02', 'Ổ cắm (Máy chiếu)', 'online', 'Gắn trần (cho máy chiếu)', @hub_ha801_mesh1, @conn_zwave, 1),
    ('DEV_H801_PLUG03', 'Ổ cắm (Góc sạc SV 1)', 'online', 'Tường, gần cửa sổ', @hub_ha801_mesh1, @conn_zwave, 1),
    ('DEV_H801_PLUG04', 'Ổ cắm (Góc sạc SV 2)', 'offline', 'Tường, gần cửa ra vào', @hub_ha801_mesh1, @conn_zwave, 1),
    ('DEV_H801_PLUG05', 'Ổ cắm (Máy lọc KK)', 'online', 'Tường, gần máy lọc KK', @hub_ha801_mesh1, @conn_zwave, 1),

    -- 3 Cảm biến (BLE)
    ('DEV_H801_MOT01', 'Cảm biến chuyển động (Cửa)', 'online', 'Gần cửa ra vào', @hub_ha801_mesh2, @conn_ble, 1),
    ('DEV_H801_MOT02', 'Cảm biến chuyển động (Cuối phòng)', 'online', 'Cuối phòng', @hub_ha801_mesh2, @conn_ble, 1),
    ('DEV_H801_DOOR01', 'Cảm biến cửa', 'online', 'Cửa ra vào', @hub_ha801_mesh2, @conn_ble, 1),

    -- ===============================================
    -- Thiết bị cho phòng X.A5.05 (26 thiết bị)
    -- ===============================================
    ('DEV_X505_AC01', 'Máy lạnh 1 (Trái) X.A5.05', 'online', 'Treo tường, bên trái phòng', @hub_xa505_main, @conn_wifi, 1),
    ('DEV_X505_AC02', 'Máy lạnh 2 (Phải) X.A5.05', 'online', 'Treo tường, bên phải phòng', @hub_xa505_main, @conn_wifi, 1),
    ('DEV_X505_PROJ01', 'Máy chiếu Panasonic', 'offline', 'Treo trần, giữa phòng', @hub_xa505_main, @conn_ble, 1),
    ('DEV_X505_PDU01', 'Thanh nguồn PDU (Tủ Rack)', 'online', 'Trong tủ rack máy chủ', @hub_xa505_main, @conn_wifi, 1),
    ('DEV_X505_CAM01', 'Camera giám sát X.A5.05', 'online', 'Góc phòng, gần cửa ra vào', @hub_xa505_main, @conn_wifi, 1),
    
    -- 10 Đèn (Zigbee)
    ('DEV_X505_L01', 'Đèn 1 X.A5.05', 'online', 'Trần, Dãy 1', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L02', 'Đèn 2 X.A5.05', 'online', 'Trần, Dãy 1', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L03', 'Đèn 3 X.A5.05', 'online', 'Trần, Dãy 2', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L04', 'Đèn 4 X.A5.05', 'online', 'Trần, Dãy 2', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L05', 'Đèn 5 X.A5.05', 'online', 'Trần, Dãy 3', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L06', 'Đèn 6 X.A5.05', 'online', 'Trần, Dãy 3', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L07', 'Đèn 7 X.A5.05', 'online', 'Trần, Dãy 4', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L08', 'Đèn 8 X.A5.05', 'online', 'Trần, Dãy 4', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L09', 'Đèn 9 X.A5.05', 'offline', 'Trần, Dãy 5', @hub_xa505_mesh1, @conn_zigbee, 1),
    ('DEV_X505_L10', 'Đèn 10 X.A5.05', 'online', 'Trần, Dãy 5', @hub_xa505_mesh1, @conn_zigbee, 1),
    
    -- 10 Ổ cắm (Z-Wave)
    ('DEV_X505_PLUG01', 'Ổ cắm (Máy trạm 01)', 'online', 'Dãy A, Bàn 1', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG02', 'Ổ cắm (Máy trạm 02)', 'online', 'Dãy A, Bàn 2', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG03', 'Ổ cắm (Máy trạm 03)', 'online', 'Dãy B, Bàn 1', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG04', 'Ổ cắm (Máy trạm 04)', 'online', 'Dãy B, Bàn 2', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG05', 'Ổ cắm (Máy trạm 05)', 'offline', 'Dãy C, Bàn 1', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG06', 'Ổ cắm (Máy trạm 06)', 'online', 'Dãy C, Bàn 2', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG07', 'Ổ cắm (Máy trạm 07)', 'online', 'Dãy D, Bàn 1', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG08', 'Ổ cắm (Máy trạm 08)', 'online', 'Dãy D, Bàn 2', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG09', 'Ổ cắm (Máy trạm 09)', 'online', 'Dãy E, Bàn 1', @hub_xa505_mesh2, @conn_zwave, 1),
    ('DEV_X505_PLUG10', 'Ổ cắm (Máy trạm 10)', 'online', 'Dãy E, Bàn 2', @hub_xa505_mesh2, @conn_zwave, 1),
    
    -- Cảm biến
    ('DEV_X505_MOT01', 'Cảm biến chuyển động (Cửa)', 'online', 'Gần cửa ra vào', @hub_xa505_mesh2, @conn_ble, 1),
    
    -- ===============================================
    -- Thiết bị cho Văn phòng Khoa CNTT (16 thiết bị)
    -- ===============================================
    ('DEV_VPOFF_AC01', 'Máy lạnh khu A (Giáo vụ)', 'online', 'Trần, khu A', @hub_vp_cntt_main, @conn_wifi, 1),
    ('DEV_VPOFF_AC02', 'Máy lạnh khu B (Giảng viên)', 'offline', 'Trần, khu B', @hub_vp_cntt_main, @conn_wifi, 1),
    ('DEV_VPOFF_CAM01', 'Camera giám sát (Khu A)', 'online', 'Góc phòng, khu A', @hub_vp_cntt_main, @conn_wifi, 1),
    ('DEV_VPOFF_CAM02', 'Camera giám sát (Khu B)', 'online', 'Góc phòng, khu B', @hub_vp_cntt_main, @conn_wifi, 1),
    ('DEV_VPOFF_LOCK01', 'Khóa cửa thông minh (Cửa chính)', 'online', 'Cửa ra vào', @hub_vp_cntt_main, @conn_zwave, 1),
    ('DEV_VPOFF_COFFEE01', 'Máy pha cà phê', 'online', 'Khu pantry', @hub_vp_cntt_main, @conn_wifi, 1),

    -- 4 Đèn (Zigbee)
    ('DEV_VPOFF_L01', 'Đèn (Khu A) VP Khoa', 'online', 'Trần nhà, khu A', @hub_vp_cntt_backup, @conn_zigbee, 1),
    ('DEV_VPOFF_L02', 'Đèn (Khu B) VP Khoa', 'online', 'Trần nhà, khu B', @hub_vp_cntt_backup, @conn_zigbee, 1),
    ('DEV_VPOFF_L03', 'Đèn (Phòng họp) VP Khoa', 'offline', 'Trần nhà, phòng họp', @hub_vp_cntt_backup, @conn_zigbee, 1),
    ('DEV_VPOFF_L04', 'Đèn (Pantry) VP Khoa', 'online', 'Trần nhà, pantry', @hub_vp_cntt_backup, @conn_zigbee, 1),
    
    -- 5 Ổ cắm (Z-Wave)
    ('DEV_VPOFF_PLUG01', 'Ổ cắm (Máy in)', 'online', 'Góc máy in', @hub_vp_cntt_backup, @conn_zwave, 1),
    ('DEV_VPOFF_PLUG02', 'Ổ cắm (Bàn GV 1)', 'online', 'Bàn 1, khu A', @hub_vp_cntt_backup, @conn_zwave, 1),
    ('DEV_VPOFF_PLUG03', 'Ổ cắm (Bàn GV 2)', 'online', 'Bàn 2, khu A', @hub_vp_cntt_backup, @conn_zwave, 1),
    ('DEV_VPOFF_PLUG04', 'Ổ cắm (Bàn GV 3)', 'online', 'Bàn 3, khu B', @hub_vp_cntt_backup, @conn_zwave, 1),
    ('DEV_VPOFF_PLUG05', 'Ổ cắm (Bàn GV 4)', 'online', 'Bàn 4, khu B', @hub_vp_cntt_backup, @conn_zwave, 1),
    
    -- Cảm biến
    ('DEV_VPOFF_MOT01', 'Cảm biến chuyển động (Khu A)', 'online', 'Gần cửa ra vào khu A', @hub_vp_cntt_backup, @conn_ble, 1)
ON DUPLICATE KEY UPDATE `name` = `name`;
COMMIT;

-- Lưu ID (VARCHAR) của các device
SET @dev_h801_ac01 = 'DEV_H801_AC01';
SET @dev_h801_proj01 = 'DEV_H801_PROJ01';
SET @dev_h801_airpur01 = 'DEV_H801_AIRPUR01';
SET @dev_h801_cam01 = 'DEV_H801_CAM01';
SET @dev_h801_l01 = 'DEV_H801_L01';
SET @dev_h801_l02 = 'DEV_H801_L02';
SET @dev_h801_l03 = 'DEV_H801_L03';
SET @dev_h801_l04 = 'DEV_H801_L04';
SET @dev_h801_l05 = 'DEV_H801_L05';
SET @dev_h801_l06 = 'DEV_H801_L06';
SET @dev_h801_l07 = 'DEV_H801_L07';
SET @dev_h801_l08 = 'DEV_H801_L08';
SET @dev_h801_plug01 = 'DEV_H801_PLUG01';
SET @dev_h801_plug02 = 'DEV_H801_PLUG02';
SET @dev_h801_plug03 = 'DEV_H801_PLUG03';
SET @dev_h801_plug04 = 'DEV_H801_PLUG04';
SET @dev_h801_plug05 = 'DEV_H801_PLUG05';
SET @dev_h801_mot01 = 'DEV_H801_MOT01';
SET @dev_h801_mot02 = 'DEV_H801_MOT02';
SET @dev_h801_door01 = 'DEV_H801_DOOR01';

SET @dev_x505_ac01 = 'DEV_X505_AC01';
SET @dev_x505_ac02 = 'DEV_X505_AC02';
SET @dev_x505_proj01 = 'DEV_X505_PROJ01';
SET @dev_x505_pdu01 = 'DEV_X505_PDU01';
SET @dev_x505_cam01 = 'DEV_X505_CAM01';
SET @dev_x505_l01 = 'DEV_X505_L01';
SET @dev_x505_l02 = 'DEV_X505_L02';
SET @dev_x505_l03 = 'DEV_X505_L03';
SET @dev_x505_l04 = 'DEV_X505_L04';
SET @dev_x505_l05 = 'DEV_X505_L05';
SET @dev_x505_l06 = 'DEV_X505_L06';
SET @dev_x505_l07 = 'DEV_X505_L07';
SET @dev_x505_l08 = 'DEV_X505_L08';
SET @dev_x505_l09 = 'DEV_X505_L09';
SET @dev_x505_l10 = 'DEV_X505_L10';
SET @dev_x505_plug01 = 'DEV_X505_PLUG01';
SET @dev_x505_plug02 = 'DEV_X505_PLUG02';
SET @dev_x505_plug03 = 'DEV_X505_PLUG03';
SET @dev_x505_plug04 = 'DEV_X505_PLUG04';
SET @dev_x505_plug05 = 'DEV_X505_PLUG05';
SET @dev_x505_plug06 = 'DEV_X505_PLUG06';
SET @dev_x505_plug07 = 'DEV_X505_PLUG07';
SET @dev_x505_plug08 = 'DEV_X505_PLUG08';
SET @dev_x505_plug09 = 'DEV_X505_PLUG09';
SET @dev_x505_plug10 = 'DEV_X505_PLUG10';
SET @dev_x505_mot01 = 'DEV_X505_MOT01';

SET @dev_vpoff_ac01 = 'DEV_VPOFF_AC01';
SET @dev_vpoff_ac02 = 'DEV_VPOFF_AC02';
SET @dev_vpoff_cam01 = 'DEV_VPOFF_CAM01';
SET @dev_vpoff_cam02 = 'DEV_VPOFF_CAM02';
SET @dev_vpoff_lock01 = 'DEV_VPOFF_LOCK01';
SET @dev_vpoff_coffee01 = 'DEV_VPOFF_COFFEE01';
SET @dev_vpoff_l01 = 'DEV_VPOFF_L01';
SET @dev_vpoff_l02 = 'DEV_VPOFF_L02';
SET @dev_vpoff_l03 = 'DEV_VPOFF_L03';
SET @dev_vpoff_l04 = 'DEV_VPOFF_L04';
SET @dev_vpoff_plug01 = 'DEV_VPOFF_PLUG01';
SET @dev_vpoff_plug02 = 'DEV_VPOFF_PLUG02';
SET @dev_vpoff_plug03 = 'DEV_VPOFF_PLUG03';
SET @dev_vpoff_plug04 = 'DEV_VPOFF_PLUG04';
SET @dev_vpoff_plug05 = 'DEV_VPOFF_PLUG05';
SET @dev_vpoff_mot01 = 'DEV_VPOFF_MOT01';

-- -----------------------------------------------------
-- Bảng 6: sensor (Tăng CỰC KỲ GIÀU)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `sensor` (`id`, `name`, `status`, `location`, `device_id`, `v`)
VALUES
    -- Cảm biến cho H.A8.01
    ('SEN_H801_AC01_TEMP', 'Cảm biến nhiệt độ (ML H.A8.01)', 'online', 'Tích hợp', @dev_h801_ac01, 1),
    ('SEN_H801_AC01_HUMID', 'Cảm biến độ ẩm (ML H.A8.01)', 'online', 'Tích hợp', @dev_h801_ac01, 1),
    ('SEN_H801_PROJ01_LIGHT', 'Cảm biến ánh sáng (Máy chiếu)', 'online', 'Tích hợp', @dev_h801_proj01, 1),
    ('SEN_H801_AIRPUR01_PM25', 'Cảm biến PM2.5 (Máy lọc KK)', 'online', 'Tích hợp', @dev_h801_airpur01, 1),
    ('SEN_H801_AIRPUR01_AQI', 'Cảm biến AQI (Máy lọc KK)', 'online', 'Tích hợp', @dev_h801_airpur01, 1),
    ('SEN_H801_L01_LIGHT', 'Cảm biến ánh sáng (Đèn 1)', 'online', 'Tích hợp', @dev_h801_l01, 1),
    ('SEN_H801_L02_LIGHT', 'Cảm biến ánh sáng (Đèn 2)', 'online', 'Tích hợp', @dev_h801_l02, 1),
    ('SEN_H801_L03_LIGHT', 'Cảm biến ánh sáng (Đèn 3)', 'offline', 'Tích hợp', @dev_h801_l03, 1),
    ('SEN_H801_L04_LIGHT', 'Cảm biến ánh sáng (Đèn 4)', 'online', 'Tích hợp', @dev_h801_l04, 1),
    ('SEN_H801_L05_LIGHT', 'Cảm biến ánh sáng (Đèn 5)', 'online', 'Tích hợp', @dev_h801_l05, 1),
    ('SEN_H801_L06_LIGHT', 'Cảm biến ánh sáng (Đèn 6)', 'online', 'Tích hợp', @dev_h801_l06, 1),
    ('SEN_H801_L07_LIGHT', 'Cảm biến ánh sáng (Đèn 7)', 'online', 'Tích hợp', @dev_h801_l07, 1),
    ('SEN_H801_L08_LIGHT', 'Cảm biến ánh sáng (Đèn 8)', 'online', 'Tích hợp', @dev_h801_l08, 1),
    ('SEN_H801_PLUG01_POWER', 'Cảm biến công suất (Bàn GV)', 'online', 'Tích hợp', @dev_h801_plug01, 1),
    ('SEN_H801_PLUG01_VOLT', 'Cảm biến điện áp (Bàn GV)', 'online', 'Tích hợp', @dev_h801_plug01, 1),
    ('SEN_H801_PLUG01_ENERGY', 'Cảm biến tổng điện năng (Bàn GV)', 'online', 'Tích hợp', @dev_h801_plug01, 1),
    ('SEN_H801_PLUG02_POWER', 'Cảm biến công suất (Máy chiếu)', 'online', 'Tích hợp', @dev_h801_plug02, 1),
    ('SEN_H801_PLUG02_ENERGY', 'Cảm biến tổng điện năng (Máy chiếu)', 'online', 'Tích hợp', @dev_h801_plug02, 1),
    ('SEN_H801_PLUG03_POWER', 'Cảm biến công suất (Sạc 1)', 'online', 'Tích hợp', @dev_h801_plug03, 1),
    ('SEN_H801_PLUG04_POWER', 'Cảm biến công suất (Sạc 2)', 'offline', 'Tích hợp', @dev_h801_plug04, 1),
    ('SEN_H801_PLUG05_POWER', 'Cảm biến công suất (Máy lọc KK)', 'online', 'Tích hợp', @dev_h801_plug05, 1),
    ('SEN_H801_PLUG05_ENERGY', 'Cảm biến tổng điện năng (Máy lọc KK)', 'online', 'Tích hợp', @dev_h801_plug05, 1),
    ('SEN_H801_MOT01_MOTION', 'Cảm biến (Motion Cửa)', 'online', 'Tích hợp', @dev_h801_mot01, 1),
    ('SEN_H801_MOT02_MOTION', 'Cảm biến (Motion Cuối phòng)', 'online', 'Tích hợp', @dev_h801_mot02, 1),
    ('SEN_H801_DOOR01_STATE', 'Cảm biến (Trạng thái cửa)', 'online', 'Tích hợp', @dev_h801_door01, 1),

    -- Cảm biến cho X.A5.05
    ('SEN_X505_AC01_TEMP', 'Cảm biến nhiệt độ (ML1 X.A5.05)', 'online', 'Tích hợp', @dev_x505_ac01, 1),
    ('SEN_X505_AC01_HUMID', 'Cảm biến độ ẩm (ML1 X.A5.05)', 'online', 'Tích hợp', @dev_x505_ac01, 1),
    ('SEN_X505_AC02_TEMP', 'Cảm biến nhiệt độ (ML2 X.A5.05)', 'online', 'Tích hợp', @dev_x505_ac02, 1),
    ('SEN_X505_AC02_HUMID', 'Cảm biến độ ẩm (ML2 X.A5.05)', 'online', 'Tích hợp', @dev_x505_ac02, 1),
    ('SEN_X505_PROJ01_LIGHT', 'Cảm biến ánh sáng (Máy chiếu)', 'offline', 'Tích hợp', @dev_x505_proj01, 1),
    ('SEN_X505_PDU01_POWER', 'Cảm biến công suất (PDU)', 'online', 'Tích hợp', @dev_x505_pdu01, 1),
    ('SEN_X505_PDU01_VOLT', 'Cảm biến điện áp (PDU)', 'online', 'Tích hợp', @dev_x505_pdu01, 1),
    ('SEN_X505_PDU01_ENERGY', 'Cảm biến tổng điện năng (PDU)', 'online', 'Tích hợp', @dev_x505_pdu01, 1),
    ('SEN_X505_L01_LIGHT', 'Cảm biến ánh sáng (Đèn 1)', 'online', 'Tích hợp', @dev_x505_l01, 1),
    ('SEN_X505_L02_LIGHT', 'Cảm biến ánh sáng (Đèn 2)', 'online', 'Tích hợp', @dev_x505_l02, 1),
    ('SEN_X505_L03_LIGHT', 'Cảm biến ánh sáng (Đèn 3)', 'online', 'Tích hợp', @dev_x505_l03, 1),
    ('SEN_X505_L04_LIGHT', 'Cảm biến ánh sáng (Đèn 4)', 'online', 'Tích hợp', @dev_x505_l04, 1),
    ('SEN_X505_L05_LIGHT', 'Cảm biến ánh sáng (Đèn 5)', 'online', 'Tích hợp', @dev_x505_l05, 1),
    ('SEN_X505_L06_LIGHT', 'Cảm biến ánh sáng (Đèn 6)', 'online', 'Tích hợp', @dev_x505_l06, 1),
    ('SEN_X505_L07_LIGHT', 'Cảm biến ánh sáng (Đèn 7)', 'online', 'Tích hợp', @dev_x505_l07, 1),
    ('SEN_X505_L08_LIGHT', 'Cảm biến ánh sáng (Đèn 8)', 'online', 'Tích hợp', @dev_x505_l08, 1),
    ('SEN_X505_L09_LIGHT', 'Cảm biến ánh sáng (Đèn 9)', 'offline', 'Tích hợp', @dev_x505_l09, 1),
    ('SEN_X505_L10_LIGHT', 'Cảm biến ánh sáng (Đèn 10)', 'online', 'Tích hợp', @dev_x505_l10, 1),
    ('SEN_X505_PLUG01_POWER', 'Cảm biến công suất (Máy trạm 01)', 'online', 'Tích hợp', @dev_x505_plug01, 1),
    ('SEN_X505_PLUG02_POWER', 'Cảm biến công suất (Máy trạm 02)', 'online', 'Tích hợp', @dev_x505_plug02, 1),
    ('SEN_X505_PLUG03_POWER', 'Cảm biến công suất (Máy trạm 03)', 'online', 'Tích hợp', @dev_x505_plug03, 1),
    ('SEN_X505_PLUG04_POWER', 'Cảm biến công suất (Máy trạm 04)', 'online', 'Tích hợp', @dev_x505_plug04, 1),
    ('SEN_X505_PLUG05_POWER', 'Cảm biến công suất (Máy trạm 05)', 'offline', 'Tích hợp', @dev_x505_plug05, 1),
    ('SEN_X505_PLUG06_POWER', 'Cảm biến công suất (Máy trạm 06)', 'online', 'Tích hợp', @dev_x505_plug06, 1),
    ('SEN_X505_PLUG07_POWER', 'Cảm biến công suất (Máy trạm 07)', 'online', 'Tích hợp', @dev_x505_plug07, 1),
    ('SEN_X505_PLUG08_POWER', 'Cảm biến công suất (Máy trạm 08)', 'online', 'Tích hợp', @dev_x505_plug08, 1),
    ('SEN_X505_PLUG09_POWER', 'Cảm biến công suất (Máy trạm 09)', 'online', 'Tích hợp', @dev_x505_plug09, 1),
    ('SEN_X505_PLUG10_POWER', 'Cảm biến công suất (Máy trạm 10)', 'online', 'Tích hợp', @dev_x505_plug10, 1),
    ('SEN_X505_MOT01_MOTION', 'Cảm biến (Motion Cửa)', 'online', 'Tích hợp', @dev_x505_mot01, 1),

    -- Cảm biến cho Văn phòng Khoa CNTT
    ('SEN_VPOFF_AC01_TEMP', 'Cảm biến nhiệt độ (ML Khu A)', 'online', 'Tích hợp', @dev_vpoff_ac01, 1),
    ('SEN_VPOFF_AC01_HUMID', 'Cảm biến độ ẩm (ML Khu A)', 'online', 'Tích hợp', @dev_vpoff_ac01, 1),
    ('SEN_VPOFF_AC02_TEMP', 'Cảm biến nhiệt độ (ML Khu B)', 'offline', 'Tích hợp', @dev_vpoff_ac02, 1),
    ('SEN_VPOFF_AC02_HUMID', 'Cảm biến độ ẩm (ML Khu B)', 'offline', 'Tích hợp', @dev_vpoff_ac02, 1),
    ('SEN_VPOFF_LOCK01_STATE', 'Cảm biến (Trạng thái cửa chính)', 'online', 'Tích hợp', @dev_vpoff_lock01, 1),
    ('SEN_VPOFF_COFFEE01_POWER', 'Cảm biến công suất (Máy cà phê)', 'online', 'Tích hợp', @dev_vpoff_coffee01, 1),
    ('SEN_VPOFF_COFFEE01_ENERGY', 'Cảm biến tổng điện năng (Máy cà phê)', 'online', 'Tích hợp', @dev_vpoff_coffee01, 1),
    ('SEN_VPOFF_L01_LIGHT', 'Cảm biến ánh sáng (Đèn Khu A)', 'online', 'Tích hợp', @dev_vpoff_l01, 1),
    ('SEN_VPOFF_L02_LIGHT', 'Cảm biến ánh sáng (Đèn Khu B)', 'online', 'Tích hợp', @dev_vpoff_l02, 1),
    ('SEN_VPOFF_L03_LIGHT', 'Cảm biến ánh sáng (Đèn Phòng họp)', 'offline', 'Tích hợp', @dev_vpoff_l03, 1),
    ('SEN_VPOFF_L04_LIGHT', 'Cảm biến ánh sáng (Đèn Pantry)', 'online', 'Tích hợp', @dev_vpoff_l04, 1),
    ('SEN_VPOFF_PLUG01_POWER', 'Cảm biến công suất (Máy in)', 'online', 'Tích hợp', @dev_vpoff_plug01, 1),
    ('SEN_VPOFF_PLUG02_POWER', 'Cảm biến công suất (Bàn GV 1)', 'online', 'Tích hợp', @dev_vpoff_plug02, 1),
    ('SEN_VPOFF_PLUG03_POWER', 'Cảm biến công suất (Bàn GV 2)', 'online', 'Tích hợp', @dev_vpoff_plug03, 1),
    ('SEN_VPOFF_PLUG04_POWER', 'Cảm biến công suất (Bàn GV 3)', 'online', 'Tích hợp', @dev_vpoff_plug04, 1),
    ('SEN_VPOFF_PLUG05_POWER', 'Cảm biến công suất (Bàn GV 4)', 'online', 'Tích hợp', @dev_vpoff_plug05, 1),
    ('SEN_VPOFF_MOT01_MOTION', 'Cảm biến (Motion Khu A)', 'online', 'Tích hợp', @dev_vpoff_mot01, 1)
ON DUPLICATE KEY UPDATE `name` = `name`;
COMMIT;

-- Lưu ID (VARCHAR) của các sensor
SET @sensor_h801_ac01_temp = 'SEN_H801_AC01_TEMP';
SET @sensor_h801_ac01_humid = 'SEN_H801_AC01_HUMID';
SET @sensor_h801_proj01_light = 'SEN_H801_PROJ01_LIGHT';
SET @sensor_h801_airpur01_pm25 = 'SEN_H801_AIRPUR01_PM25';
SET @sensor_h801_airpur01_aqi = 'SEN_H801_AIRPUR01_AQI';
SET @sensor_h801_l01_light = 'SEN_H801_L01_LIGHT';
SET @sensor_h801_l02_light = 'SEN_H801_L02_LIGHT';
SET @sensor_h801_l03_light = 'SEN_H801_L03_LIGHT';
SET @sensor_h801_l04_light = 'SEN_H801_L04_LIGHT';
SET @sensor_h801_l05_light = 'SEN_H801_L05_LIGHT';
SET @sensor_h801_l06_light = 'SEN_H801_L06_LIGHT';
SET @sensor_h801_l07_light = 'SEN_H801_L07_LIGHT';
SET @sensor_h801_l08_light = 'SEN_H801_L08_LIGHT';
SET @sensor_h801_plug01_power = 'SEN_H801_PLUG01_POWER';
SET @sensor_h801_plug01_volt = 'SEN_H801_PLUG01_VOLT';
SET @sensor_h801_plug01_energy = 'SEN_H801_PLUG01_ENERGY';
SET @sensor_h801_plug02_power = 'SEN_H801_PLUG02_POWER';
SET @sensor_h801_plug02_energy = 'SEN_H801_PLUG02_ENERGY';
SET @sensor_h801_plug03_power = 'SEN_H801_PLUG03_POWER';
SET @sensor_h801_plug04_power = 'SEN_H801_PLUG04_POWER';
SET @sensor_h801_plug05_power = 'SEN_H801_PLUG05_POWER';
SET @sensor_h801_plug05_energy = 'SEN_H801_PLUG05_ENERGY';
SET @sensor_h801_mot01_motion = 'SEN_H801_MOT01_MOTION';
SET @sensor_h801_mot02_motion = 'SEN_H801_MOT02_MOTION';
SET @sensor_h801_door01_state = 'SEN_H801_DOOR01_STATE';
SET @sensor_x505_ac01_temp = 'SEN_X505_AC01_TEMP';
SET @sensor_x505_ac01_humid = 'SEN_X505_AC01_HUMID';
SET @sensor_x505_ac02_temp = 'SEN_X505_AC02_TEMP';
SET @sensor_x505_ac02_humid = 'SEN_X505_AC02_HUMID';
SET @sensor_x505_proj01_light = 'SEN_X505_PROJ01_LIGHT';
SET @sensor_x505_pdu01_power = 'SEN_X505_PDU01_POWER';
SET @sensor_x505_pdu01_volt = 'SEN_X505_PDU01_VOLT';
SET @sensor_x505_pdu01_energy = 'SEN_X505_PDU01_ENERGY';
SET @sensor_x505_l01_light = 'SEN_X505_L01_LIGHT';
SET @sensor_x505_l02_light = 'SEN_X505_L02_LIGHT';
SET @sensor_x505_l03_light = 'SEN_X505_L03_LIGHT';
SET @sensor_x505_l04_light = 'SEN_X505_L04_LIGHT';
SET @sensor_x505_l05_light = 'SEN_X505_L05_LIGHT';
SET @sensor_x505_l06_light = 'SEN_X505_L06_LIGHT';
SET @sensor_x505_l07_light = 'SEN_X505_L07_LIGHT';
SET @sensor_x505_l08_light = 'SEN_X505_L08_LIGHT';
SET @sensor_x505_l09_light = 'SEN_X505_L09_LIGHT';
SET @sensor_x505_l10_light = 'SEN_X505_L10_LIGHT';
SET @sensor_x505_plug01_power = 'SEN_X505_PLUG01_POWER';
SET @sensor_x505_plug02_power = 'SEN_X505_PLUG02_POWER';
SET @sensor_x505_plug03_power = 'SEN_X505_PLUG03_POWER';
SET @sensor_x505_plug04_power = 'SEN_X505_PLUG04_POWER';
SET @sensor_x505_plug05_power = 'SEN_X505_PLUG05_POWER';
SET @sensor_x505_plug06_power = 'SEN_X505_PLUG06_POWER';
SET @sensor_x505_plug07_power = 'SEN_X505_PLUG07_POWER';
SET @sensor_x505_plug08_power = 'SEN_X505_PLUG08_POWER';
SET @sensor_x505_plug09_power = 'SEN_X505_PLUG09_POWER';
SET @sensor_x505_plug10_power = 'SEN_X505_PLUG10_POWER';
SET @sensor_x505_mot01_motion = 'SEN_X505_MOT01_MOTION';
SET @sensor_vpoff_ac01_temp = 'SEN_VPOFF_AC01_TEMP';
SET @sensor_vpoff_ac01_humid = 'SEN_VPOFF_AC01_HUMID';
SET @sensor_vpoff_ac02_temp = 'SEN_VPOFF_AC02_TEMP';
SET @sensor_vpoff_ac02_humid = 'SEN_VPOFF_AC02_HUMID';
SET @sensor_vpoff_lock01_state = 'SEN_VPOFF_LOCK01_STATE';
SET @sensor_vpoff_coffee01_power = 'SEN_VPOFF_COFFEE01_POWER';
SET @sensor_vpoff_coffee01_energy = 'SEN_VPOFF_COFFEE01_ENERGY';
SET @sensor_vpoff_l01_light = 'SEN_VPOFF_L01_LIGHT';
SET @sensor_vpoff_l02_light = 'SEN_VPOFF_L02_LIGHT';
SET @sensor_vpoff_l03_light = 'SEN_VPOFF_L03_LIGHT';
SET @sensor_vpoff_l04_light = 'SEN_VPOFF_L04_LIGHT';
SET @sensor_vpoff_plug01_power = 'SEN_VPOFF_PLUG01_POWER';
SET @sensor_vpoff_plug02_power = 'SEN_VPOFF_PLUG02_POWER';
SET @sensor_vpoff_plug03_power = 'SEN_VPOFF_PLUG03_POWER';
SET @sensor_vpoff_plug04_power = 'SEN_VPOFF_PLUG04_POWER';
SET @sensor_vpoff_plug05_power = 'SEN_VPOFF_PLUG05_POWER';
SET @sensor_vpoff_mot01_motion = 'SEN_VPOFF_MOT01_MOTION';


-- -----------------------------------------------------
-- Bảng 8: sensor_data_type (Bảng Nối)
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `sensor_data_type` (`sensor_id`, `data_type_id`, `v`)
VALUES
    -- H.A8.01
    (@sensor_h801_ac01_temp, @data_temp, 1),
    (@sensor_h801_ac01_humid, @data_humid, 1),
    (@sensor_h801_proj01_light, @data_light, 1),
    (@sensor_h801_airpur01_pm25, @data_pm25, 1),
    (@sensor_h801_airpur01_aqi, @data_aqi, 1),
    (@sensor_h801_l01_light, @data_light, 1),
    (@sensor_h801_l02_light, @data_light, 1),
    (@sensor_h801_l03_light, @data_light, 1),
    (@sensor_h801_l04_light, @data_light, 1),
    (@sensor_h801_l05_light, @data_light, 1),
    (@sensor_h801_l06_light, @data_light, 1),
    (@sensor_h801_l07_light, @data_light, 1),
    (@sensor_h801_l08_light, @data_light, 1),
    (@sensor_h801_plug01_power, @data_power, 1),
    (@sensor_h801_plug01_volt, @data_volt, 1),
    (@sensor_h801_plug01_energy, @data_energy, 1),
    (@sensor_h801_plug02_power, @data_power, 1),
    (@sensor_h801_plug02_energy, @data_energy, 1),
    (@sensor_h801_plug03_power, @data_power, 1),
    (@sensor_h801_plug04_power, @data_power, 1),
    (@sensor_h801_plug05_power, @data_power, 1),
    (@sensor_h801_plug05_energy, @data_energy, 1),
    (@sensor_h801_mot01_motion, @data_motion, 1),
    (@sensor_h801_mot02_motion, @data_motion, 1),
    (@sensor_h801_door01_state, @data_door, 1),
    
    -- X.A5.05
    (@sensor_x505_ac01_temp, @data_temp, 1),
    (@sensor_x505_ac01_humid, @data_humid, 1),
    (@sensor_x505_ac02_temp, @data_temp, 1),
    (@sensor_x505_ac02_humid, @data_humid, 1),
    (@sensor_x505_proj01_light, @data_light, 1),
    (@sensor_x505_pdu01_power, @data_power, 1),
    (@sensor_x505_pdu01_volt, @data_volt, 1),
    (@sensor_x505_pdu01_energy, @data_energy, 1),
    (@sensor_x505_l01_light, @data_light, 1),
    (@sensor_x505_l02_light, @data_light, 1),
    (@sensor_x505_l03_light, @data_light, 1),
    (@sensor_x505_l04_light, @data_light, 1),
    (@sensor_x505_l05_light, @data_light, 1),
    (@sensor_x505_l06_light, @data_light, 1),
    (@sensor_x505_l07_light, @data_light, 1),
    (@sensor_x505_l08_light, @data_light, 1),
    (@sensor_x505_l09_light, @data_light, 1),
    (@sensor_x505_l10_light, @data_light, 1),
    (@sensor_x505_plug01_power, @data_power, 1),
    (@sensor_x505_plug02_power, @data_power, 1),
    (@sensor_x505_plug03_power, @data_power, 1),
    (@sensor_x505_plug04_power, @data_power, 1),
    (@sensor_x505_plug05_power, @data_power, 1),
    (@sensor_x505_plug06_power, @data_power, 1),
    (@sensor_x505_plug07_power, @data_power, 1),
    (@sensor_x505_plug08_power, @data_power, 1),
    (@sensor_x505_plug09_power, @data_power, 1),
    (@sensor_x505_plug10_power, @data_power, 1),
    (@sensor_x505_mot01_motion, @data_motion, 1),

    -- Văn phòng Khoa CNTT
    (@sensor_vpoff_ac01_temp, @data_temp, 1),
    (@sensor_vpoff_ac01_humid, @data_humid, 1),
    (@sensor_vpoff_ac02_temp, @data_temp, 1),
    (@sensor_vpoff_ac02_humid, @data_humid, 1),
    (@sensor_vpoff_lock01_state, @data_door, 1),
    (@sensor_vpoff_coffee01_power, @data_power, 1),
    (@sensor_vpoff_coffee01_energy, @data_energy, 1),
    (@sensor_vpoff_l01_light, @data_light, 1),
    (@sensor_vpoff_l02_light, @data_light, 1),
    (@sensor_vpoff_l03_light, @data_light, 1),
    (@sensor_vpoff_l04_light, @data_light, 1),
    (@sensor_vpoff_plug01_power, @data_power, 1),
    (@sensor_vpoff_plug02_power, @data_power, 1),
    (@sensor_vpoff_plug03_power, @data_power, 1),
    (@sensor_vpoff_plug04_power, @data_power, 1),
    (@sensor_vpoff_plug05_power, @data_power, 1),
    (@sensor_vpoff_mot01_motion, @data_motion, 1)
ON DUPLICATE KEY UPDATE `sensor_id` = `sensor_id`;
COMMIT;


-- -----------------------------------------------------
-- Bảng 7: sensor_data (PHẦN QUAN TRỌNG NHẤT)
--
-- Xóa dữ liệu cũ (nếu có)
TRUNCATE TABLE `sensor_data`;
--
-- Định nghĩa Stored Procedure để sinh dữ liệu
-- -----------------------------------------------------
DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_GenerateSensorData`$$

CREATE PROCEDURE `sp_GenerateSensorData`(
    IN p_sensor_id VARCHAR(255),
    IN p_data_type_id BIGINT,
    IN p_total_minutes_back INT,
    IN p_interval_minutes INT,
    IN p_min_value DOUBLE,
    IN p_max_value DOUBLE,
    IN p_is_binary BOOLEAN
)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE v_current_time DATETIME;
    DECLARE v_recorded_at DATETIME;
    DECLARE v_random_value DOUBLE;
    
    SET v_current_time = NOW();
    
    WHILE i <= p_total_minutes_back DO
        SET v_recorded_at = DATE_SUB(v_current_time, INTERVAL i MINUTE);
        
        IF p_is_binary THEN
            -- Tạo giá trị 0 hoặc 1
            SET v_random_value = FLOOR(RAND() * 2); 
        ELSE
            -- Tạo giá trị double ngẫu nhiên
            SET v_random_value = p_min_value + (RAND() * (p_max_value - p_min_value));
        END IF;

        -- Chỉ INSERT nếu sensor và data_type_id thực sự tồn tại trong bảng nối
        IF (SELECT 1 FROM `sensor_data_type` WHERE `sensor_id` = p_sensor_id AND `data_type_id` = p_data_type_id) THEN
            INSERT INTO `sensor_data` (`value`, `created_at`, `recorded_at`, `sensor_id`, `data_type_id`)
            VALUES
                (v_random_value, v_current_time, v_recorded_at, p_sensor_id, p_data_type_id);
        END IF;
        
        SET i = i + p_interval_minutes;
    END WHILE;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- Gọi Stored Procedure để sinh dữ liệu cho 24h qua (1440 phút),
-- cách nhau 15 phút (tổng cộng ~96 điểm dữ liệu / cảm biến)
-- -----------------------------------------------------
START TRANSACTION;

-- Dữ liệu phòng H.A8.01
CALL sp_GenerateSensorData(@sensor_h801_ac01_temp, @data_temp, 1440, 15, 22.0, 28.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_ac01_humid, @data_humid, 1440, 15, 55.0, 75.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_proj01_light, @data_light, 1440, 15, 300.0, 800.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_airpur01_pm25, @data_pm25, 1440, 15, 5.0, 40.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_airpur01_aqi, @data_aqi, 1440, 15, 20.0, 110.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l01_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l02_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l03_light, @data_light, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_h801_l04_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l05_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l06_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l07_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_l08_light, @data_light, 1440, 15, 400.0, 600.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_plug01_power, @data_power, 1440, 15, 70.0, 150.0, 0); -- Bàn GV
CALL sp_GenerateSensorData(@sensor_h801_plug01_volt, @data_volt, 1440, 15, 218.0, 222.0, 0);
CALL sp_GenerateSensorData(@sensor_h801_plug01_energy, @data_energy, 1440, 1440, 15.0, 15.5, 0); -- Chỉ 1 giá trị tổng
CALL sp_GenerateSensorData(@sensor_h801_plug02_power, @data_power, 1440, 15, 250.0, 350.0, 0); -- Máy chiếu
CALL sp_GenerateSensorData(@sensor_h801_plug02_energy, @data_energy, 1440, 1440, 8.2, 8.3, 0);
CALL sp_GenerateSensorData(@sensor_h801_plug03_power, @data_power, 1440, 15, 0.0, 45.0, 0); -- Sạc SV
CALL sp_GenerateSensorData(@sensor_h801_plug04_power, @data_power, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_h801_plug05_power, @data_power, 1440, 15, 25.0, 50.0, 0); -- Máy lọc KK
CALL sp_GenerateSensorData(@sensor_h801_plug05_energy, @data_energy, 1440, 1440, 1.5, 1.6, 0);
CALL sp_GenerateSensorData(@sensor_h801_mot01_motion, @data_motion, 1440, 15, 0.0, 1.0, 1); -- Binary
CALL sp_GenerateSensorData(@sensor_h801_mot02_motion, @data_motion, 1440, 15, 0.0, 1.0, 1); -- Binary
CALL sp_GenerateSensorData(@sensor_h801_door01_state, @data_door, 1440, 15, 0.0, 1.0, 1); -- Binary

-- Dữ liệu phòng X.A5.05
CALL sp_GenerateSensorData(@sensor_x505_ac01_temp, @data_temp, 1440, 15, 20.0, 25.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_ac01_humid, @data_humid, 1440, 15, 50.0, 70.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_ac02_temp, @data_temp, 1440, 15, 20.0, 25.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_ac02_humid, @data_humid, 1440, 15, 50.0, 70.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_proj01_light, @data_light, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_x505_pdu01_power, @data_power, 1440, 15, 1500.0, 2500.0, 0); -- PDU tủ Rack
CALL sp_GenerateSensorData(@sensor_x505_pdu01_volt, @data_volt, 1440, 15, 219.0, 221.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_pdu01_energy, @data_energy, 1440, 1440, 150.0, 155.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l01_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l02_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l03_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l04_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l05_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l06_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l07_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l08_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_l09_light, @data_light, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_x505_l10_light, @data_light, 1440, 15, 500.0, 700.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug01_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug02_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug03_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug04_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug05_power, @data_power, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_x505_plug06_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug07_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug08_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug09_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_plug10_power, @data_power, 1440, 15, 120.0, 200.0, 0);
CALL sp_GenerateSensorData(@sensor_x505_mot01_motion, @data_motion, 1440, 15, 0.0, 1.0, 1); -- Binary

-- Dữ liệu Văn phòng Khoa CNTT
CALL sp_GenerateSensorData(@sensor_vpoff_ac01_temp, @data_temp, 1440, 15, 24.0, 26.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_ac01_humid, @data_humid, 1440, 15, 60.0, 70.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_ac02_temp, @data_temp, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_vpoff_ac02_humid, @data_humid, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_vpoff_lock01_state, @data_door, 1440, 30, 0.0, 1.0, 1); -- Binary, 30p 1 lần
CALL sp_GenerateSensorData(@sensor_vpoff_coffee01_power, @data_power, 1440, 15, 0.0, 1500.0, 0); -- Lúc pha 1500W, lúc nghỉ 0W
CALL sp_GenerateSensorData(@sensor_vpoff_coffee01_energy, @data_energy, 1440, 1440, 22.1, 22.2, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_l01_light, @data_light, 1440, 15, 450.0, 550.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_l02_light, @data_light, 1440, 15, 450.0, 550.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_l03_light, @data_light, 1440, 15, 0.0, 0.0, 0); -- Offline
CALL sp_GenerateSensorData(@sensor_vpoff_l04_light, @data_light, 1440, 15, 300.0, 400.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_plug01_power, @data_power, 1440, 15, 5.0, 300.0, 0); -- Máy in
CALL sp_GenerateSensorData(@sensor_vpoff_plug02_power, @data_power, 1440, 15, 80.0, 180.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_plug03_power, @data_power, 1440, 15, 80.0, 180.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_plug04_power, @data_power, 1440, 15, 80.0, 180.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_plug05_power, @data_power, 1440, 15, 80.0, 180.0, 0);
CALL sp_GenerateSensorData(@sensor_vpoff_mot01_motion, @data_motion, 1440, 15, 0.0, 1.0, 1); -- Binary

COMMIT;

-- -----------------------------------------------------
-- Xóa Stored Procedure sau khi hoàn tất
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS `sp_GenerateSensorData`;