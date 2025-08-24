--liquibase formatted sql

--changeset krainov:currency_id_change_to_readable

UPDATE node n
SET currencyid = c.short_name
FROM currency c
WHERE n.currencyid = c.id;

