ALTER TABLE users ADD COLUMN inclusion_date DATETIME NOT NULL;
ALTER TABLE users ADD COLUMN last_update_date DATETIME NOT NULL;

ALTER TABLE rooms ADD COLUMN inclusion_date DATETIME NOT NULL;
ALTER TABLE rooms ADD COLUMN last_update_date DATETIME NOT NULL;

ALTER TABLE schedules ADD COLUMN schedule_date DATETIME NOT NULL;
ALTER TABLE schedules ADD COLUMN inclusion_date DATETIME NOT NULL;
