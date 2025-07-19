
--liquibase formatted sql

--changeset kraynov.n:demo-user-data


--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: fas_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fas_user VALUES ('fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', 'demo', '$2a$10$wm8I8vVjumozG/zZqyGsqe./lJQmBNILe2.dxo2DCIh3tC6LJGtEW');


--
-- Data for Name: node; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.node VALUES ('11c88632-7c07-43a0-a323-cf8c280dae9c', 'Работа', 'Моя работа на заводе. Это внешняя нода, то есть ее баланс не будет учитываться в общей сумме или в изменении баланса.', '2', -700000.00000000, true, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, false);
INSERT INTO public.node VALUES ('098f5e7b-77ab-4900-b121-d0c1b1f8162b', 'Расходы ', 'Евровые расходы. Это внешняя нода, то есть ее баланс не будет учитываться в общей сумме или в изменении баланса.', '4', 700.00000000, true, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, false);
INSERT INTO public.node VALUES ('e6394d5d-91dc-421f-a79d-acfaedbd3aa3', 'Петрович', 'Наш баланс с Петровичем - если я ему должен, то баланс отрицательный, если он мне то положительный. Это overdraft нода - она может иметь отрицательный баланс. Это НЕ внешняя нода, потому что если я должен Петровичу то это должно отражаться в статитстике.', '2', -8000.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, false);
INSERT INTO public.node VALUES ('c8183f2d-77a2-469f-9097-956c9070a45e', 'Карта Сбер', 'Дебетовая карта Сбербанк', '2', 245500.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, false);
INSERT INTO public.node VALUES ('553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 'Рублевые расходы. Это внешняя нода, то есть ее баланс не будет учитываться в общей сумме или в изменении баланса.', '2', 148000.00000000, true, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, false);
INSERT INTO public.node VALUES ('ae277543-4e13-4200-9ab1-d49ec19f4593', 'Карта Тинькофф', 'Дебетовая карта Тинькофф банк', '2', 19500.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, false);
INSERT INTO public.node VALUES ('161df217-4463-4123-87d1-2f8148c4baa0', 'Сидорович', 'Наш баланс с Сидоровичем. Сидоровича уволили с завода, поэтому эта нода заархивированна. Архивировать можно только ноды с нулевым балансом.', '2', 0.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, true);
INSERT INTO public.node VALUES ('6204d087-5bd3-4661-b696-ab34fdca9f00', 'Депозит за квартиру в Сочи', 'Эта нода нужна для учета того что я внес депозит. Хотя этих денег в моем распоряжении нет, я планирую получить их назад, поэтому они должны учитываться в общей сумме и статистике. Поэтому это НЕ внешняя нода.', '2', 14000.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, false);
INSERT INTO public.node VALUES ('0334c94b-f072-4907-9259-6269b40e9fb2', 'Наличка', 'Наличные евро у меня на руках', '4', 530.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, false);
INSERT INTO public.node VALUES ('dd710fd7-a361-4a83-be56-27f92a58f0b5', 'Наличка', 'Наличные рубли у меня на руках', '2', 58000.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, false);
INSERT INTO public.node VALUES ('f400d503-d5e2-4e60-80a9-3c81a17d7442', 'Карта Bank of Georgia', 'Дебетовая карта BOG. В этом банке карта может иметь отрицательный баланс, поэтому эта нода overdraft.', '1', 1100.00000000, false, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, false);
INSERT INTO public.node VALUES ('bcf77c3a-8a5c-46c3-b2dc-f4a0222aeb43', 'Расходы', 'Долларовые расходы. Это внешняя нода, то есть ее баланс не будет учитываться в общей сумме или в изменении баланса.', '1', 400.00000000, true, 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', true, false);


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.transaction
VALUES ('86c6c8de-f920-4dd8-8bdf-82c30e3b6b5b', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '5 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 1);
INSERT INTO public.transaction
VALUES ('f22d1079-1bb1-4265-8902-d00b3d3d1f13', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '4 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 2);
INSERT INTO public.transaction
VALUES ('ed96bb66-6e2c-4af3-b284-7a1c03da8415', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '3 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 3);
INSERT INTO public.transaction
VALUES ('7651679a-ce17-44e1-923a-41f94b4fcc8c', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '2 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 4);
INSERT INTO public.transaction
VALUES ('ff1e57ec-deda-4cb4-9fcd-58c4f9c735b1', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '1 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 5);
INSERT INTO public.transaction
VALUES ('10753746-0a06-4807-88c2-83fd4c03322a', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '1 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 6);
INSERT INTO public.transaction
VALUES ('ce37a58f-d119-4fd3-a2b1-1893649bf8e8', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '6 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 7);
INSERT INTO public.transaction
VALUES ('c4a0ce3b-5540-41b2-be89-12b4cf1dfa28', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Премия', 150000.00000000, 150000.00000000, NOW() - INTERVAL '1 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 8);
INSERT INTO public.transaction
VALUES ('27bd40f1-092c-498f-991d-c574eff8e539', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '7 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 9);
INSERT INTO public.transaction
VALUES ('6ac3791a-328c-4c27-accb-c32d99636f76', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '8 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 10);
INSERT INTO public.transaction
VALUES ('e19d2cf8-5883-4efb-a340-af2b4324af85', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '9 MONTH',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 11);
INSERT INTO public.transaction
VALUES ('0c70694b-6b35-4ea4-97c2-07e8eddfe298', '11c88632-7c07-43a0-a323-cf8c280dae9c',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Зарплата', 50000.00000000, 50000.00000000, NOW() - INTERVAL '10 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 12);
INSERT INTO public.transaction
VALUES ('e7fe436a-b74d-454a-aeb7-82a0482feb00', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '0334c94b-f072-4907-9259-6269b40e9fb2', 'Прикупил евро по 80руб', 80000.00000000, 1000.00000000,
        NOW() - INTERVAL '1 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 13);
INSERT INTO public.transaction
VALUES ('82812439-9f13-48d3-b1a5-3712def044a7', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        'ae277543-4e13-4200-9ab1-d49ec19f4593', 'Перевел на Тинек', 230000.00000000, 230000.00000000,
        NOW() - INTERVAL '15 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 14);
INSERT INTO public.transaction
VALUES ('e57acdaa-f17c-4718-994e-5a3a465c0283', 'ae277543-4e13-4200-9ab1-d49ec19f4593',
        'dd710fd7-a361-4a83-be56-27f92a58f0b5', 'Снял', 45000.00000000, 45000.00000000, NOW() - INTERVAL '21 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 15);
INSERT INTO public.transaction
VALUES ('840a44fe-a62d-4bbb-b179-15372a5e0d2f', 'ae277543-4e13-4200-9ab1-d49ec19f4593',
        'e6394d5d-91dc-421f-a79d-acfaedbd3aa3', 'Занял до получки', 13000.00000000, 13000.00000000,
        NOW() - INTERVAL '80 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 16);
INSERT INTO public.transaction
VALUES ('82367ecb-abde-42fd-a262-fd2b0f2e5b3a', 'e6394d5d-91dc-421f-a79d-acfaedbd3aa3',
        'dd710fd7-a361-4a83-be56-27f92a58f0b5', 'Петрович отдал наличкой', 13000.00000000, 13000.00000000,
        NOW() - INTERVAL '29 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 17);
INSERT INTO public.transaction
VALUES ('b2babc91-5b4f-4685-9bef-e61c356539bf', 'ae277543-4e13-4200-9ab1-d49ec19f4593',
        '161df217-4463-4123-87d1-2f8148c4baa0', 'Занял до получки', 3500.00000000, 3500.00000000,
        NOW() - INTERVAL '34 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 18);
INSERT INTO public.transaction
VALUES ('f5c9cdec-aeed-4c4f-9faf-515df2a40a43', '161df217-4463-4123-87d1-2f8148c4baa0',
        'c8183f2d-77a2-469f-9097-956c9070a45e', 'Возврат долга', 3500.00000000, 3500.00000000,
        NOW() - INTERVAL '30 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 19);
INSERT INTO public.transaction
VALUES ('54b5a8c4-35e7-4ded-b280-0d8d5acb9c68', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 18000.00000000, 18000.00000000, NOW() - INTERVAL '17 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 20);
INSERT INTO public.transaction
VALUES ('6c7201f6-a95a-4f6b-88ad-0eb7d17f2204', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 18000.00000000, 18000.00000000, NOW() - INTERVAL '47 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 22);
INSERT INTO public.transaction
VALUES ('9f54880b-3ad4-4396-baaa-ff8a2e894d56', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 15000.00000000, 15000.00000000, NOW() - INTERVAL '77 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 21);
INSERT INTO public.transaction
VALUES ('0c65025a-82f0-411b-897f-dbe2e30f9cd4', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 21000.00000000, 21000.00000000, NOW() - INTERVAL '107 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 23);
INSERT INTO public.transaction
VALUES ('2c779333-edd3-43d0-872a-ec0e00134860', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 13000.00000000, 13000.00000000, NOW() - INTERVAL '137 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 24);
INSERT INTO public.transaction
VALUES ('272698e0-028c-460a-a381-6367a4fa9599', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 24000.00000000, 24000.00000000, NOW() - INTERVAL '167 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 25);
INSERT INTO public.transaction
VALUES ('3e96f0a1-a271-4284-b61b-84684ee5ee98', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 10000.00000000, 10000.00000000, NOW() - INTERVAL '197 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 26);
INSERT INTO public.transaction
VALUES ('21d8478d-106b-42c4-8866-829344f4ba61', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 10000.00000000, 10000.00000000, NOW() - INTERVAL '220 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 27);
INSERT INTO public.transaction
VALUES ('640dc2b5-9a00-4dce-8ca7-88333edc6099', 'c8183f2d-77a2-469f-9097-956c9070a45e',
        '553f1eab-caa5-4de4-8ad7-3dd25f1daefe', 'Расходы', 19000.00000000, 19000.00000000, NOW() - INTERVAL '250 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 28);
INSERT INTO public.transaction
VALUES ('8fc4431a-1c87-4f3a-848e-a7a906556c65', 'ae277543-4e13-4200-9ab1-d49ec19f4593',
        '6204d087-5bd3-4661-b696-ab34fdca9f00', 'Депозит', 14000.00000000, 14000.00000000, NOW() - INTERVAL '46 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 29);
INSERT INTO public.transaction
VALUES ('643a917a-e6cd-4b29-91d3-86063f3357e9', 'ae277543-4e13-4200-9ab1-d49ec19f4593',
        'f400d503-d5e2-4e60-80a9-3c81a17d7442', 'Перевел по 80руб за доллар', 120000.00000000, 1500.00000000,
        NOW() - INTERVAL '1 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 30);
INSERT INTO public.transaction
VALUES ('b0051d23-41a0-4e2d-8717-27e4a9f18b4b', 'f400d503-d5e2-4e60-80a9-3c81a17d7442',
        'bcf77c3a-8a5c-46c3-b2dc-f4a0222aeb43', 'Траты в Грузии', 400.00000000, 400.00000000, NOW() - INTERVAL '43 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 31);
INSERT INTO public.transaction
VALUES ('01cf9a64-26c4-4cd6-b00f-b84ca123703e', '0334c94b-f072-4907-9259-6269b40e9fb2',
        '098f5e7b-77ab-4900-b121-d0c1b1f8162b', 'Траты в Испании', 700.00000000, 700.00000000, NOW() - INTERVAL '4 DAY',
        'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 32);
INSERT INTO public.transaction
VALUES ('f454c954-7098-452d-a98d-44a624d516a0', 'e6394d5d-91dc-421f-a79d-acfaedbd3aa3',
        'ae277543-4e13-4200-9ab1-d49ec19f4593', 'Занял у Петровича до получки', 8000.00000000, 8000.00000000,
        NOW() - INTERVAL '2 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 33);
INSERT INTO public.transaction
VALUES ('0f3f49a4-7437-4754-84ca-f244c0aade87', 'ae277543-4e13-4200-9ab1-d49ec19f4593',
        '0334c94b-f072-4907-9259-6269b40e9fb2', 'Прикупил евро по 100руб', 23000.00000000, 230.00000000,
        NOW() - INTERVAL '1 DAY', 'fddaec08-fdb6-4986-b26d-d9b2ec1f3dda', false, 34);


--
-- Name: transaction_order_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_order_seq', 34, true);


--
-- PostgreSQL database dump complete
--

