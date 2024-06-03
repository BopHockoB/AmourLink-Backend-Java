CREATE TRIGGER IF NOT EXISTS shift_positions_after_delete AFTER DELETE ON picture
    FOR EACH ROW
BEGIN
    UPDATE picture SET position = position - 1 WHERE position > OLD.position;
END;