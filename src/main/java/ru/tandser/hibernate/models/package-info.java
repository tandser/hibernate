@GenericGenerator(
        name       = "ID_GENERATOR",
        strategy   = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "JPWH_SEQUENCE"),
                @Parameter(name = "initial_value", value = "1000")
        }
)

package ru.tandser.hibernate.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;