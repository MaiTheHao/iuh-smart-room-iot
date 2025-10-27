-- -- Tạo và sử dụng cơ sở dữ liệu
DROP DATABASE IF EXISTS smart_room_iot;

CREATE DATABASE IF NOT EXISTS smart_room_iot CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smart_room_iot;

-- -----------------------------------------------------
-- Bảng 1: room (Không có phụ thuộc)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS room (
        `id` VARCHAR(255) PRIMARY KEY,
        `name` VARCHAR(255) NOT NULL,
        `location` VARCHAR(255),
        `description` TEXT,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0 COMMENT 'Version for optimistic locking'
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 2: connection_type (Bảng tra cứu - Không có phụ thuộc)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS connection_type (
        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
        `code` VARCHAR(50) NOT NULL UNIQUE,
        `name` VARCHAR(255) NOT NULL,
        `description` TEXT,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 3: data_type (Bảng tra cứu - Không có phụ thuộc)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS data_type (
        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
        `code` VARCHAR(50) NOT NULL UNIQUE,
        `name` VARCHAR(255) NOT NULL,
        `unit` VARCHAR(50) COMMENT 'ví dụ: C, %, kWh',
        `description` TEXT,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 4: hub (Phụ thuộc vào `room`)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS hub (
        `id` VARCHAR(255) PRIMARY KEY,
        `name` VARCHAR(255) NOT NULL,
        `status` VARCHAR(100),
        `location` VARCHAR(255) COMMENT 'Vị trí tương đối của hub trong room.',
        `description` TEXT,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0,
        `room_id` VARCHAR(100) NOT NULL,
        FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 5: device (Phụ thuộc vào `hub` và `connection_type`)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS device (
        `id` VARCHAR(255) PRIMARY KEY,
        `name` VARCHAR(255) NOT NULL,
        `status` VARCHAR(100),
        `location` VARCHAR(255) COMMENT 'Vị trí tương đối của device so với hub.',
        `description` TEXT,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0,
        `hub_id` VARCHAR(100) NOT NULL,
        `connection_type_id` BIGINT NOT NULL,
        FOREIGN KEY (`hub_id`) REFERENCES `hub` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (`connection_type_id`) REFERENCES `connection_type` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 6: sensor (Phụ thuộc vào `device`)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS sensor (
        `id` VARCHAR(255) PRIMARY KEY,
        `name` VARCHAR(255) NOT NULL,
        `status` VARCHAR(100),
        `location` VARCHAR(255) COMMENT 'Vị trí tương đối của sensor so với device.',
        `description` TEXT,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0,
        `device_id` VARCHAR(100) NOT NULL,
        FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 7: sensor_data (Bảng Time-series)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS sensor_data (
        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
        `value` DOUBLE NOT NULL,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `recorded_at` TIMESTAMP NOT NULL,
        `sensor_id` VARCHAR(100) NOT NULL,
        `data_type_id` BIGINT NOT NULL,
        FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`) ON DELETE CASCADE,
        FOREIGN KEY (`data_type_id`) REFERENCES `data_type` (`id`) ON DELETE RESTRICT,
        INDEX `idx_sensor_recorded_at` (`sensor_id`, `recorded_at` DESC),
        INDEX `idx_recorded_at` (`recorded_at` DESC)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Bảng 8: sensor_data_type (Bảng Nối Many-to-Many)
-- -----------------------------------------------------
CREATE TABLE
    IF NOT EXISTS sensor_data_type (
        `sensor_id` VARCHAR(100) NOT NULL,
        `data_type_id` BIGINT NOT NULL,
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `created_by` INT,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `updated_by` INT,
        `v` INT NOT NULL DEFAULT 0,
        PRIMARY KEY (`sensor_id`, `data_type_id`),
        FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`) ON DELETE CASCADE,
        FOREIGN KEY (`data_type_id`) REFERENCES `data_type` (`id`) ON DELETE CASCADE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;