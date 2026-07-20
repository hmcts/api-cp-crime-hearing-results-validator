package uk.gov.hmcts.cp.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.cp.openapi.api.RootApi;
import uk.gov.hmcts.cp.openapi.api.ValidationApi;
import uk.gov.hmcts.cp.openapi.api.ValidationRulesApi;
import uk.gov.hmcts.cp.openapi.model.AffectedDefendant;
import uk.gov.hmcts.cp.openapi.model.AffectedOffence;
import uk.gov.hmcts.cp.openapi.model.DefendantDto;
import uk.gov.hmcts.cp.openapi.model.DraftValidationRequest;
import uk.gov.hmcts.cp.openapi.model.DraftValidationResponse;
import uk.gov.hmcts.cp.openapi.model.ErrorResponse;
import uk.gov.hmcts.cp.openapi.model.OffenceDto;
import uk.gov.hmcts.cp.openapi.model.ResultLineDto;
import uk.gov.hmcts.cp.openapi.model.RuleDetailResponse;
import uk.gov.hmcts.cp.openapi.model.RuleListResponse;
import uk.gov.hmcts.cp.openapi.model.ValidationErrors;
import uk.gov.hmcts.cp.openapi.model.ValidationIssue;

import java.lang.reflect.Field;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenApiObjectsTest {

    @Test
    void generated_error_response_should_have_expected_fields() {
        assertThat(ErrorResponse.class).hasDeclaredMethods("error", "message", "details", "traceId");
    }

    @Test
    void generated_error_response_timestamp_should_be_instant() throws Exception {
        Field timestampField = ErrorResponse.class.getDeclaredField("timestamp");
        assertThat(timestampField.getType())
                .as("timestamp field type")
                .isEqualTo(Instant.class);
    }

    @Test
    void generated_root_api_should_have_expected_methods() {
        assertThat(RootApi.class).hasDeclaredMethods("getRoot");
    }

    @Test
    void generated_validation_api_should_have_expected_methods() {
        assertThat(ValidationApi.class).hasDeclaredMethods("validateDraftResults");
    }

    @Test
    void generated_validation_rules_api_should_have_expected_methods() {
        assertThat(ValidationRulesApi.class).hasDeclaredMethods("listValidationRules", "getValidationRuleById");
    }

    @Test
    void generated_draft_validation_request_should_have_expected_fields() {
        assertThat(DraftValidationRequest.class).hasDeclaredFields(
                "hearingId", "caseId", "hearingDay", "courtType", "resultLines", "defendants", "offences"
        );
    }

    @Test
    void generated_draft_validation_response_should_have_expected_fields() {
        assertThat(DraftValidationResponse.class).hasDeclaredFields(
                "validationId", "timestamp", "mode", "rulesEvaluated", "isValid", "errors", "warnings", "processingTimeMs"
        );
    }

    @Test
    void generated_validation_issue_should_have_expected_fields() {
        assertThat(ValidationIssue.class).hasDeclaredFields(
                "ruleId", "severity", "affectedResultCodes", "affectedOffences", "affectedDefendants", "validationLevel"
        );
    }

    @Test
    void generated_result_line_dto_should_have_expected_fields() {
        assertThat(ResultLineDto.class).hasDeclaredFields(
                "resultLineId", "shortCode", "label", "defendantId", "offenceId", "isConcurrent", "consecutiveToOffence", "category"
        );
    }

    @Test
    void generated_defendant_dto_should_have_expected_fields() {
        assertThat(DefendantDto.class).hasDeclaredFields("defendantId", "firstName", "lastName", "dateOfBirth");
    }

    @Test
    void generated_defendant_dto_date_of_birth_should_be_local_date() throws Exception {
        Field dateOfBirthField = DefendantDto.class.getDeclaredField("dateOfBirth");
        assertThat(dateOfBirthField.getType())
                .as("dateOfBirth field type")
                .isEqualTo(java.time.LocalDate.class);
    }

    @Test
    void generated_offence_dto_should_have_expected_fields() {
        assertThat(OffenceDto.class).hasDeclaredFields(
                "offenceId", "offenceCode", "offenceTitle", "hasActiveElectronicMonitoring", "orderIndex", "caseUrn"
        );
    }

    @Test
    void generated_rule_list_response_should_have_expected_fields() {
        assertThat(RuleListResponse.class).hasDeclaredFields("count", "enabledCount", "rules");
    }

    @Test
    void generated_rule_detail_response_should_have_expected_fields() {
        assertThat(RuleDetailResponse.class).hasDeclaredFields(
                "ruleId", "title", "description", "priority", "severity", "enabled"
        );
    }

    @Test
    void generated_affected_offence_should_have_expected_fields() {
        assertThat(AffectedOffence.class).hasDeclaredFields("offenceId", "offenceTitle", "message");
    }

    @Test
    void generated_affected_defendant_should_have_expected_fields() {
        assertThat(AffectedDefendant.class).hasDeclaredFields("defendantId", "message");
    }

    @Test
    void generated_validation_errors_should_have_expected_fields() {
        assertThat(ValidationErrors.class).hasDeclaredFields("errorMessages", "validationIssues");
    }
}
