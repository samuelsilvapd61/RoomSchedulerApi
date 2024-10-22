ALTER TABLE schedules
ADD CONSTRAINT fk_room_schedules
FOREIGN KEY (room_id)
REFERENCES rooms(id)
ON DELETE CASCADE;

ALTER TABLE schedules
ADD CONSTRAINT fk_user_schedules
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE;
