-- liquibase formatted sql

-- changeset fanzi03:add-tag
CREATE TABLE items(
	id UUID PRIMARY KEY,
	name VARCHAR(55) NOT NULL,
	description VARCHAR(150),
	primary_tag VARCHAR(55)
);

CREATE TABLE item_tags(
	item_id UUID NOT NULL REFERENCES items(id) ON DELETE CASCADE,
	tag VARCHAR(55) NOT NULL,
	PRIMARY KEY (item_id, tag)
);

CREATE INDEX idx_item_tags_tag ON item_tags(tag);
