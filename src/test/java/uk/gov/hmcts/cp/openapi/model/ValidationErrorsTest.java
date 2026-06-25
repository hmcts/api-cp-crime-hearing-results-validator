package uk.gov.hmcts.cp.openapi.model;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.cp.openapi.model.ValidationIssue.SeverityEnum;
import uk.gov.hmcts.cp.openapi.model.ValidationIssue.ValidationLevelEnum;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationErrorsTest {

    @Test
    void no_arg_constructor_initialises_empty_lists() {
        ValidationErrors subject = new ValidationErrors();

        assertThat(subject.getErrorMessages()).isNotNull().isEmpty();
        assertThat(subject.getValidationIssues()).isNotNull().isEmpty();
    }

    @Test
    void builder_produces_object_with_expected_values() {
        ValidationIssue issue = ValidationIssue.builder()
                .ruleId("DR-SENT-001")
                .severity(SeverityEnum.ERROR)
                .build();

        ValidationErrors subject = ValidationErrors.builder()
                .errorMessages(List.of("Sentencing error on offence 1"))
                .validationIssues(List.of(issue))
                .build();

        assertThat(subject.getErrorMessages()).containsExactly("Sentencing error on offence 1");
        assertThat(subject.getValidationIssues()).containsExactly(issue);
    }

    @Test
    void add_error_messages_item_appends_to_list() {
        ValidationErrors subject = new ValidationErrors();

        subject.addErrorMessagesItem("first error");
        subject.addErrorMessagesItem("second error");

        assertThat(subject.getErrorMessages()).containsExactly("first error", "second error");
    }

    @Test
    void add_validation_issues_item_appends_to_list() {
        ValidationErrors subject = new ValidationErrors();
        ValidationIssue issue1 = ValidationIssue.builder().ruleId("DR-SENT-001").build();
        ValidationIssue issue2 = ValidationIssue.builder().ruleId("DR-SENT-002").build();

        subject.addValidationIssuesItem(issue1);
        subject.addValidationIssuesItem(issue2);

        assertThat(subject.getValidationIssues()).containsExactly(issue1, issue2);
    }

    @Test
    void fluent_error_messages_setter_returns_same_instance() {
        ValidationErrors subject = new ValidationErrors();
        List<String> messages = List.of("error A");

        ValidationErrors returned = subject.errorMessages(messages);

        assertThat(returned).isSameAs(subject);
        assertThat(subject.getErrorMessages()).isEqualTo(messages);
    }

    @Test
    void fluent_validation_issues_setter_returns_same_instance() {
        ValidationErrors subject = new ValidationErrors();
        List<ValidationIssue> issues = List.of(ValidationIssue.builder().ruleId("DR-SENT-001").build());

        ValidationErrors returned = subject.validationIssues(issues);

        assertThat(returned).isSameAs(subject);
        assertThat(subject.getValidationIssues()).isEqualTo(issues);
    }

    @Test
    void set_error_messages_replaces_list() {
        ValidationErrors subject = new ValidationErrors();
        subject.addErrorMessagesItem("old message");

        subject.setErrorMessages(List.of("new message"));

        assertThat(subject.getErrorMessages()).containsExactly("new message");
    }

    @Test
    void set_validation_issues_replaces_list() {
        ValidationErrors subject = new ValidationErrors();
        ValidationIssue oldIssue = ValidationIssue.builder().ruleId("OLD-001").build();
        ValidationIssue newIssue = ValidationIssue.builder().ruleId("NEW-001").build();
        subject.addValidationIssuesItem(oldIssue);

        subject.setValidationIssues(List.of(newIssue));

        assertThat(subject.getValidationIssues()).containsExactly(newIssue);
    }

    @Test
    void equals_returns_true_for_objects_with_same_content() {
        ValidationErrors a = ValidationErrors.builder()
                .errorMessages(List.of("msg"))
                .build();
        ValidationErrors b = ValidationErrors.builder()
                .errorMessages(List.of("msg"))
                .build();

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_returns_false_for_different_error_messages() {
        ValidationErrors a = ValidationErrors.builder()
                .errorMessages(List.of("msg1"))
                .build();
        ValidationErrors b = ValidationErrors.builder()
                .errorMessages(List.of("msg2"))
                .build();

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_returns_false_for_different_validation_issues() {
        ValidationIssue issue1 = ValidationIssue.builder().ruleId("DR-SENT-001").build();
        ValidationIssue issue2 = ValidationIssue.builder().ruleId("DR-SENT-002").build();

        ValidationErrors a = ValidationErrors.builder()
                .errorMessages(List.of("msg"))
                .validationIssues(List.of(issue1))
                .build();
        ValidationErrors b = ValidationErrors.builder()
                .errorMessages(List.of("msg"))
                .validationIssues(List.of(issue2))
                .build();

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_returns_false_for_null() {
        ValidationErrors subject = new ValidationErrors();
        assertThat(subject).isNotEqualTo(null);
    }

    @Test
    void equals_returns_false_for_different_type() {
        ValidationErrors subject = new ValidationErrors();
        assertThat(subject).isNotEqualTo("not a ValidationErrors");
    }

    @Test
    void equals_returns_true_for_same_instance() {
        ValidationErrors subject = new ValidationErrors();
        assertThat(subject).isEqualTo(subject);
    }

    @Test
    void hash_code_is_equal_for_objects_with_same_content() {
        ValidationErrors a = ValidationErrors.builder()
                .errorMessages(List.of("msg"))
                .build();
        ValidationErrors b = ValidationErrors.builder()
                .errorMessages(List.of("msg"))
                .build();

        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    @Test
    void to_string_contains_error_messages() {
        ValidationErrors subject = ValidationErrors.builder()
                .errorMessages(List.of("Concurrent with no IMP"))
                .build();

        assertThat(subject.toString()).contains("errorMessages").contains("Concurrent with no IMP");
    }

    @Test
    void to_string_contains_validation_issues() {
        ValidationErrors subject = ValidationErrors.builder()
                .validationIssues(List.of(ValidationIssue.builder().ruleId("DR-SENT-001").build()))
                .build();

        assertThat(subject.toString()).contains("validationIssues").contains("DR-SENT-001");
    }

    @Test
    void get_error_messages_method_is_annotated_with_not_null() throws Exception {
        var method = ValidationErrors.class.getDeclaredMethod("getErrorMessages");
        assertThat(method.isAnnotationPresent(NotNull.class)).isTrue();
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

    @Test
    void validation_level_enum_from_value_offence() {
        assertThat(ValidationLevelEnum.fromValue("OFFENCE")).isEqualTo(ValidationLevelEnum.OFFENCE);
    }

    @Test
    void validation_level_enum_from_value_defendant() {
        assertThat(ValidationLevelEnum.fromValue("DEFENDANT")).isEqualTo(ValidationLevelEnum.DEFENDANT);
    }

    @Test
    void validation_level_enum_from_unknown_value_throws_illegal_argument_exception() {
        assertThatThrownBy(() -> ValidationLevelEnum.fromValue("CASE"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CASE");
    }

    @Test
    void validation_level_enum_get_value_returns_string() {
        assertThat(ValidationLevelEnum.OFFENCE.getValue()).isEqualTo("OFFENCE");
        assertThat(ValidationLevelEnum.DEFENDANT.getValue()).isEqualTo("DEFENDANT");
    }

    @Test
    void validation_errors_with_multiple_issues_and_messages_round_trips_correctly() {
        ValidationIssue error = ValidationIssue.builder()
                .ruleId("DR-SENT-001")
                .severity(SeverityEnum.ERROR)
                .validationLevel(ValidationLevelEnum.OFFENCE)
                .affectedOffences(List.of(
                        AffectedOffence.builder().offenceId("OFF-1").message("IMP missing").build()
                ))
                .build();

        ValidationIssue warning = ValidationIssue.builder()
                .ruleId("DR-SENT-100")
                .severity(SeverityEnum.WARNING)
                .validationLevel(ValidationLevelEnum.DEFENDANT)
                .affectedDefendants(List.of(
                        AffectedDefendant.builder().defendantId("DEF-1").message("EM order active").build()
                ))
                .build();

        ValidationErrors subject = ValidationErrors.builder()
                .errorMessages(List.of("Sentence is missing a required IMP result"))
                .validationIssues(List.of(error, warning))
                .build();

        assertThat(subject.getErrorMessages()).hasSize(1);
        assertThat(subject.getValidationIssues()).hasSize(2);
        assertThat(subject.getValidationIssues().get(0).getRuleId()).isEqualTo("DR-SENT-001");
        assertThat(subject.getValidationIssues().get(1).getRuleId()).isEqualTo("DR-SENT-100");
    }
}
