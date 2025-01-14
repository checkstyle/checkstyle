-- stop tab check
-- // OK below, no violation as TRATATATATA is not a tab character on line
  SELECT * FROM users
-- resume tab check
-- // violation below, 'MESSAGE HERE'.
  SELECT 1