CREATE TABLE "irori_objects"
(
	id serial NOT NULL,
	name text NOT NULL,
	type text NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT name_unique UNIQUE (name)
);

CREATE TABLE "irori_stats"
(
	id serial NOT NULL,
	object integer NOT NULL,
	stat text NOT NULL,
	value text,
	PRIMARY KEY (id),
	CONSTRAINT unique_object_stat UNIQUE (stat, object),
	CONSTRAINT fk_object_id FOREIGN KEY (object)
		REFERENCES irori.irori_objects (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
);