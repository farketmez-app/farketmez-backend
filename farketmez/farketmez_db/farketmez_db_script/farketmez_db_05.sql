-- Ortalama etkinlik puanı için trigger eklendi.
CREATE OR REPLACE FUNCTION calculate_event_average_rating()
RETURNS TRIGGER AS $$
DECLARE
    totalRating NUMERIC;
    ratingCount INT;
BEGIN
    SELECT SUM(rating), COUNT(rating) INTO totalRating, ratingCount
    FROM participants
    WHERE event_id = NEW.event_id AND rating IS NOT NULL;

    -- Etkinliğin yeni ortalama puanını hesapla ve güncelle
    IF ratingCount > 0 THEN
        UPDATE events
        SET average_rating = totalRating / ratingCount
        WHERE id = NEW.event_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_event_average_rating
AFTER INSERT OR UPDATE ON participants
FOR EACH ROW
EXECUTE FUNCTION calculate_event_average_rating();