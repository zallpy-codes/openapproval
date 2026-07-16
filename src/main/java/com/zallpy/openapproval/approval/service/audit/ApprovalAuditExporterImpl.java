package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Default implementation of {@link ApprovalAuditExporter}.
 *
 * <p>
 * Exports approval audit history.
 * </p>
 *
 * <p>
 * V1 exports audit history as CSV.
 * Future milestones will support:
 * <ul>
 * <li>Excel</li>
 * <li>PDF</li>
 * <li>JSON</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalAuditExporterImpl
        implements ApprovalAuditExporter {

    /**
     * Audit history service.
     */
    private final ApprovalAuditHistoryService historyService;

    /**
     * CSV delimiter.
     */
    private static final String DELIMITER = ",";

    /**
     * Line separator.
     */
    private static final String NEW_LINE = System.lineSeparator();

    /**
     * Date formatter.
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] exportWorkflowHistory(
            final UUID workflowId) {

        List<ApprovalAudit> history = historyService.findWorkflowHistory(
                workflowId);

        StringBuilder builder = new StringBuilder();

        builder.append(
                "Occurred At")
                .append(DELIMITER)
                .append("Action")
                .append(DELIMITER)
                .append("Actor Type")
                .append(DELIMITER)
                .append("Performed By")
                .append(DELIMITER)
                .append("Affected User")
                .append(DELIMITER)
                .append("Event Type")
                .append(DELIMITER)
                .append("Comment")
                .append(NEW_LINE);

        for (ApprovalAudit audit : history) {

            builder.append(
                    value(audit.getOccurredAt()))
                    .append(DELIMITER)

                    .append(
                            value(audit.getAction()))
                    .append(DELIMITER)

                    .append(
                            value(audit.getActorType()))
                    .append(DELIMITER)

                    .append(
                            value(audit.getPerformedByName()))
                    .append(DELIMITER)

                    .append(
                            value(audit.getAffectedUserName()))
                    .append(DELIMITER)

                    .append(
                            value(audit.getEventType()))
                    .append(DELIMITER)

                    .append(
                            escape(audit.getComment()))
                    .append(NEW_LINE);
        }

        return builder.toString()
                .getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Converts an object to a CSV-safe value.
     *
     * @param value object value
     * @return string value
     */
    private String value(
            final Object value) {

        if (value == null) {
            return "";
        }

        if (value instanceof java.time.LocalDateTime dateTime) {
            return DATE_FORMATTER.format(dateTime);
        }

        return value.toString();
    }

    /**
     * Escapes CSV values.
     *
     * @param value value
     * @return escaped value
     */
    private String escape(
            final String value) {

        if (value == null) {
            return "";
        }

        return "\"" +
                value.replace("\"", "\"\"") +
                "\"";
    }

}