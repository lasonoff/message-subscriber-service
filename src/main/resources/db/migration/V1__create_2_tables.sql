CREATE TABLE agent
(
    agent_id     varchar(300) NOT NULL,
    manufactured varchar(300) NOT NULL,
    os           varchar(50)  NOT NULL,
    PRIMARY KEY (agent_id)
);

CREATE TABLE telemetry
(
    uuid                  varchar(300) NOT NULL,
    agent_id              varchar(300) NOT NULL,
    previous_message_time bigint       NOT NULL,
    active_service        varchar(300) NOT NULL,
    quality_score         integer      NOT NULL,
    PRIMARY KEY (uuid),
    FOREIGN KEY (agent_id) references agent (agent_id)
);