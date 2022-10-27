CREATE SCHEMA app;

CREATE TYPE app.status AS ENUM (
    'out_of_stock',
    'in_stock',
    'running_low'
);

CREATE TABLE app.order_items (
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL
);

CREATE TABLE app.orders (
    id integer NOT NULL,
    user_id integer NOT NULL,
    status character varying,
    created_at timestamp without time zone NOT NULL
);

ALTER TABLE app.orders ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE app.products (
    id integer NOT NULL,
    name character varying NOT NULL,
    price integer NOT NULL,
    status app.status NOT NULL,
    created_at timestamp without time zone NOT NULL
);

ALTER TABLE app.products ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

ALTER TABLE ONLY app.products
    ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE ONLY app.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (order_id, product_id);

ALTER TABLE ONLY app.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);

ALTER TABLE ONLY app.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);

ALTER TABLE ONLY app.order_items
    ADD CONSTRAINT order_fk FOREIGN KEY (order_id) REFERENCES app.orders(id);

ALTER TABLE ONLY app.order_items
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES app.products(id) NOT VALID;

