ALTER TABLE schedules
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (room_id, schedule_date);
