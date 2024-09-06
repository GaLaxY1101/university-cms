-- Update phone numbers in the users table to match the regex ^0\d{9}$
UPDATE users
SET phone_number = '0' || RIGHT(phone_number, 9)
WHERE LENGTH(phone_number) <> 10 OR phone_number !~ '^0\d{9}$';