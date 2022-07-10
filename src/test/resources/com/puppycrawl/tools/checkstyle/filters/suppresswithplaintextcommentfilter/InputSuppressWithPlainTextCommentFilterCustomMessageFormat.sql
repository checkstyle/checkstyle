-- CHECKSTYLE stop warning COUNT
SELECT COUNT(*) FROM filters;

-- CHECKSTYLE resume warning COUNT
SELECT COUNT(*) FROM checks;

SELECT COUNT(*) FROM tests; -- CHECKSTYLE default warning COUNT

-- CHECKSTYLE stop warning NonMatchingMessage
SELECT COUNT(*) FROM reports;
