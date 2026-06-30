package uk.gov.hmcts.cp.openapi.model;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.cp.openapi.model.UpdateRuleRequest.SeverityEnum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdateRuleRequestTest {

    @Test
    void no_arg_constructor_leaves_all_fields_null() {
        UpdateRuleRequest subject = new UpdateRuleRequest();

        assertThat(subject.getEnabled()).isNull();
        assertThat(subject.getSeverity()).isNull();
    }

    @Test
    void builder_with_only_enabled_true_for_enable_scenario() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder()
                .enabled(true)
                .build();

        assertThat(subject.getEnabled()).isTrue();
        assertThat(subject.getSeverity()).isNull();
    }

    @Test
    void builder_with_only_enabled_false_for_disable_scenario() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder()
                .enabled(false)
                .build();

        assertThat(subject.getEnabled()).isFalse();
        assertThat(subject.getSeverity()).isNull();
    }

    @Test
    void builder_with_only_severity_set() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder()
                .severity(SeverityEnum.ERROR)
                .build();

        assertThat(subject.getSeverity()).isEqualTo(SeverityEnum.ERROR);
        assertThat(subject.getEnabled()).isNull();
    }

    @Test
    void builder_with_both_enabled_and_severity() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder()
                .enabled(false)
                .severity(SeverityEnum.WARNING)
                .build();

        assertThat(subject.getEnabled()).isFalse();
        assertThat(subject.getSeverity()).isEqualTo(SeverityEnum.WARNING);
    }

    @Test
    void fluent_enabled_setter_returns_same_instance() {
        UpdateRuleRequest subject = new UpdateRuleRequest();

        UpdateRuleRequest returned = subject.enabled(true);

        assertThat(returned).isSameAs(subject);
        assertThat(subject.getEnabled()).isTrue();
    }

    @Test
    void fluent_severity_setter_returns_same_instance() {
        UpdateRuleRequest subject = new UpdateRuleRequest();

        UpdateRuleRequest returned = subject.severity(SeverityEnum.ERROR);

        assertThat(returned).isSameAs(subject);
        assertThat(subject.getSeverity()).isEqualTo(SeverityEnum.ERROR);
    }

    @Test
    void set_enabled_replaces_value() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder().enabled(true).build();

        subject.setEnabled(false);

        assertThat(subject.getEnabled()).isFalse();
    }

    @Test
    void set_severity_replaces_value() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder().severity(SeverityEnum.ERROR).build();

        subject.setSeverity(SeverityEnum.WARNING);

        assertThat(subject.getSeverity()).isEqualTo(SeverityEnum.WARNING);
    }

    @Test
    void equals_returns_true_for_objects_with_same_content() {
        UpdateRuleRequest a = UpdateRuleRequest.builder().enabled(true).severity(SeverityEnum.ERROR).build();
        UpdateRuleRequest b = UpdateRuleRequest.builder().enabled(true).severity(SeverityEnum.ERROR).build();

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_returns_false_for_different_enabled_values() {
        UpdateRuleRequest a = UpdateRuleRequest.builder().enabled(true).build();
        UpdateRuleRequest b = UpdateRuleRequest.builder().enabled(false).build();

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_returns_false_for_different_severity_values() {
        UpdateRuleRequest a = UpdateRuleRequest.builder().severity(SeverityEnum.ERROR).build();
        UpdateRuleRequest b = UpdateRuleRequest.builder().severity(SeverityEnum.WARNING).build();

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void to_string_contains_enabled_and_severity_fields() {
        UpdateRuleRequest subject = UpdateRuleRequest.builder()
                .enabled(true)
                .severity(SeverityEnum.WARNING)
                .build();

        assertThat(subject.toString()).contains("enabled").contains("true");
        assertThat(subject.toString()).contains("severity").contains("WARNING");
    }

    @Test
    void severity_enum_from_value_error() {
        assertThat(SeverityEnum.fromValue("ERROR")).isEqualTo(SeverityEnum.ERROR);
    }

    @Test
    void severity_enum_from_value_warning() {
        assertThat(SeverityEnum.fromValue("WARNING")).isEqualTo(SeverityEnum.WARNING);
    }

    @Test
    void severity_enum_from_unknown_value_throws_illegal_argument_exception() {
        assertThatThrownBy(() -> SeverityEnum.fromValue("CRITICAL"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CRITICAL");
    }

    @Test
    void severity_enum_get_value_returns_string() {
        assertThat(SeverityEnum.ERROR.getValue()).isEqualTo("ERROR");
        assertThat(SeverityEnum.WARNING.getValue()).isEqualTo("WARNING");
    }
}
