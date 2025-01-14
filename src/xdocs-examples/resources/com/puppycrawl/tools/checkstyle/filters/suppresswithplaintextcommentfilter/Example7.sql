-- CSOFF join (it is ok to use join here for performance reasons)
SELECT name, job_name
FROM users AS u
JOIN jobs AS j ON u.job_id = j.id
-- CSON join

-- CSOFF count (test query execution plan)
EXPLAIN SELECT COUNT(*) FROM restaurants
-- CSON count
