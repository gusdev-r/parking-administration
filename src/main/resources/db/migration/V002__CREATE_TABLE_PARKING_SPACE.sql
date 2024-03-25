CREATE TABLE IF NOT EXISTS `tb_parking_space` (
    id LONG NOT NULL PRIMARY KEY,
    condominium_block VARCHAR(40) NOT NULL,
    condominium_apartment VARCHAR(40) NOT NULL,
    vehicle_brand VARCHAR(70) NOT NULL,
    vehicle_model VARCHAR(70) NOT NULL,
    vehicle_color VARCHAR(70) NOT NULL,
    responsible_vehicle_name VARCHAR(140) NOT NULL,
    vehicle_space_number VARCHAR(10) NOT NULL UNIQUE,
    vehicle_license_plate_number VARCHAR(7) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    user_id LONG NOT NULL PRIMARY KEY,
    vehicle_id LONG NOT NULL PRIMARY KEY

    CONSTRAINTS FOREIGN KEY (user_id) REFERENCES tb_user(id)
    CONSTRAINTS FOREIGN KEY (vehicle_id) REFERENCES tb_vehicle(id)
);