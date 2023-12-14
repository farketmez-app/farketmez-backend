ALTER TABLE users ADD COLUMN mail VARCHAR(30);
UPDATE users SET mail = 'temp@example.com' WHERE mail IS NULL; --tablo oluşturulren mail unutulduğu için varsayılan olarka bu değer ataanmıştır.
ALTER TABLE users ALTER COLUMN mail SET NOT NULL;
ALTER TABLE users DROP COLUMN token;