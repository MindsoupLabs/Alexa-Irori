CREATE TABLE "irori_aliases"
(
	id serial NOT NULL,
	alias text NOT NULL,
	object integer NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT unique_alias UNIQUE (alias),
	CONSTRAINT fk_object_id FOREIGN KEY (object)
		REFERENCES irori.irori_objects (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
);