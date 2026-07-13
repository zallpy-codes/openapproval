package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalCommentType;
import com.zallpy.openapproval.approval.enums.ApprovalCommentVisibility;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import com.zallpy.openapproval.common.entity.ActiveEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a runtime comment recorded during
 * an approval execution.
 *
 * <p>
 * Comments provide additional context, justification,
 * audit notes and communication between approvers
 * throughout the approval lifecycle.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_execution_comments", indexes = {
        @Index(name = "idx_execution_comment_execution", columnList = "approval_execution_id"),
        @Index(name = "idx_execution_comment_stage", columnList = "approval_execution_stage_id"),
        @Index(name = "idx_execution_comment_task", columnList = "approval_execution_task_id"),
        @Index(name = "idx_execution_comment_reference", columnList = "comment_reference"),
        @Index(name = "idx_execution_comment_active", columnList = "active")
})
public class ApprovalExecutionComment extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval execution.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_id", nullable = false)
    private ApprovalExecution approvalExecution;

    /**
     * Parent execution stage.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_stage_id", nullable = false)
    private ApprovalExecutionStage approvalExecutionStage;

    /**
     * Parent execution task.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_task_id", nullable = false)
    private ApprovalExecutionTask approvalExecutionTask;

    /**
     * Unique comment identifier.
     */
    @NotNull
    @Column(name = "comment_uuid", nullable = false, unique = true)
    private UUID commentUuid = UUID.randomUUID();

    /**
     * Human-readable comment reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "comment_reference", nullable = false, unique = true, length = 100)
    private String commentReference;

    /**
     * Type of comment.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type", nullable = false, length = 40)
    private ApprovalCommentType commentType = ApprovalCommentType.GENERAL;

    /**
     * Visibility of the comment.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 30)
    private ApprovalCommentVisibility visibility = ApprovalCommentVisibility.PUBLIC;

    /**
     * Comment text.
     */
    @NotBlank
    @Size(max = 4000)
    @Column(name = "comment_text", nullable = false, length = 4000)
    private String commentText;

    /**
     * Author identifier.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "author_id", nullable = false, length = 100)
    private String authorId;

    /**
     * Author name.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "author_name", nullable = false, length = 200)
    private String authorName;

    /**
     * Author email address.
     */
    @Size(max = 255)
    @Column(name = "author_email", length = 255)
    private String authorEmail;

    /**
     * Date and time the comment was created.
     */
    @NotNull
    @Column(name = "commented_at", nullable = false)
    private LocalDateTime commentedAt = LocalDateTime.now();

    /**
     * Date and time the comment was last edited.
     */
    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    /**
     * Indicates whether this is a system-generated comment.
     */
    @Column(name = "system_comment", nullable = false)
    private boolean systemComment = false;

    /**
     * Indicates whether this comment has been edited.
     */
    @Column(name = "edited", nullable = false)
    private boolean edited = false;

    /**
     * Optional metadata associated with this comment.
     *
     * <p>
     * May contain serialized JSON or additional
     * audit information.
     */
    @Size(max = 4000)
    @Column(name = "metadata", length = 4000)
    private String metadata;

    /**
     * Default constructor.
     */
    public ApprovalExecutionComment() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionComment other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    // -------------------------------------------------------------------------
    // Business Methods
    // -------------------------------------------------------------------------

    /**
     * Updates the comment text.
     *
     * @param commentText updated comment
     */
    public void edit(final String commentText) {

        if (commentText == null || commentText.isBlank()) {
            throw new IllegalArgumentException(
                    "Comment text cannot be empty.");
        }

        this.commentText = commentText.trim();
        this.edited = true;
        this.editedAt = LocalDateTime.now();
    }

    /**
     * Marks this comment as a system-generated comment.
     */
    public void markAsSystemComment() {
        this.systemComment = true;
    }

    /**
     * Marks this comment as a user-generated comment.
     */
    public void markAsUserComment() {
        this.systemComment = false;
    }

    /**
     * Updates the comment visibility.
     *
     * @param visibility new visibility
     */
    public void changeVisibility(
            final ApprovalCommentVisibility visibility) {

        if (visibility == null) {
            throw new IllegalArgumentException(
                    "Comment visibility cannot be null.");
        }

        this.visibility = visibility;
    }

    /**
     * Updates the comment type.
     *
     * @param commentType new comment type
     */
    public void changeCommentType(
            final ApprovalCommentType commentType) {

        if (commentType == null) {
            throw new IllegalArgumentException(
                    "Comment type cannot be null.");
        }

        this.commentType = commentType;
    }

    /**
     * Updates the metadata.
     *
     * @param metadata metadata
     */
    public void updateMetadata(final String metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns whether this comment has been edited.
     *
     * @return true if edited
     */
    public boolean isEdited() {
        return edited;
    }

    /**
     * Returns whether this comment is system-generated.
     *
     * @return true if system generated
     */
    public boolean isSystemComment() {
        return systemComment;
    }

    /**
     * Returns whether metadata exists.
     *
     * @return true if metadata exists
     */
    public boolean hasMetadata() {
        return metadata != null && !metadata.isBlank();
    }

    /**
     * Returns whether an author email has been provided.
     *
     * @return true if available
     */
    public boolean hasAuthorEmail() {
        return authorEmail != null
                && !authorEmail.isBlank();
    }

    /**
     * Returns whether the comment can still be edited.
     *
     * <p>
     * System-generated comments are considered read-only.
     *
     * @return true if editable
     */
    public boolean isEditable() {
        return !systemComment;
    }

    /**
     * Returns whether this is an internal comment.
     *
     * @return true if internal
     */
    public boolean isInternalComment() {
        return visibility == ApprovalCommentVisibility.INTERNAL;
    }

    /**
     * Returns whether this is an audit-only comment.
     *
     * @return true if audit only
     */
    public boolean isAuditComment() {
        return visibility == ApprovalCommentVisibility.AUDIT_ONLY;
    }

    /**
     * Returns whether this is a public comment.
     *
     * @return true if public
     */
    public boolean isPublicComment() {
        return visibility == ApprovalCommentVisibility.PUBLIC;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalExecution getApprovalExecution() {
        return approvalExecution;
    }

    public void setApprovalExecution(
            ApprovalExecution approvalExecution) {
        this.approvalExecution = approvalExecution;
    }

    public ApprovalExecutionStage getApprovalExecutionStage() {
        return approvalExecutionStage;
    }

    public void setApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage) {
        this.approvalExecutionStage = approvalExecutionStage;
    }

    public ApprovalExecutionTask getApprovalExecutionTask() {
        return approvalExecutionTask;
    }

    public void setApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask) {
        this.approvalExecutionTask = approvalExecutionTask;
    }

    public UUID getCommentUuid() {
        return commentUuid;
    }

    public void setCommentUuid(UUID commentUuid) {
        this.commentUuid = commentUuid;
    }

    public String getCommentReference() {
        return commentReference;
    }

    public void setCommentReference(String commentReference) {
        this.commentReference = commentReference;
    }

    public ApprovalCommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(ApprovalCommentType commentType) {
        this.commentType = commentType;
    }

    public ApprovalCommentVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(
            ApprovalCommentVisibility visibility) {
        this.visibility = visibility;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(LocalDateTime commentedAt) {
        this.commentedAt = commentedAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }


    public void setSystemComment(boolean systemComment) {
        this.systemComment = systemComment;
    }

 

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Validates the comment configuration.
     *
     * @throws IllegalStateException if the comment is invalid
     */
    public void validateConfiguration() {

        if (approvalExecution == null) {
            throw new IllegalStateException(
                    "Approval execution is required.");
        }

        if (approvalExecutionStage == null) {
            throw new IllegalStateException(
                    "Approval execution stage is required.");
        }

        if (approvalExecutionTask == null) {
            throw new IllegalStateException(
                    "Approval execution task is required.");
        }

        if (commentReference == null
                || commentReference.isBlank()) {

            throw new IllegalStateException(
                    "Comment reference is required.");
        }

        if (commentType == null) {
            throw new IllegalStateException(
                    "Comment type is required.");
        }

        if (visibility == null) {
            throw new IllegalStateException(
                    "Comment visibility is required.");
        }

        if (commentText == null
                || commentText.isBlank()) {

            throw new IllegalStateException(
                    "Comment text is required.");
        }

        if (authorId == null
                || authorId.isBlank()) {

            throw new IllegalStateException(
                    "Author ID is required.");
        }

        if (authorName == null
                || authorName.isBlank()) {

            throw new IllegalStateException(
                    "Author name is required.");
        }

        if (commentedAt == null) {
            throw new IllegalStateException(
                    "Comment date is required.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this comment belongs to an execution.
     *
     * @return true if linked to an execution
     */
    public boolean hasExecution() {
        return approvalExecution != null;
    }

    /**
     * Returns whether this comment belongs to a stage.
     *
     * @return true if linked to a stage
     */
    public boolean hasStage() {
        return approvalExecutionStage != null;
    }

    /**
     * Returns whether this comment belongs to a task.
     *
     * @return true if linked to a task
     */
    public boolean hasTask() {
        return approvalExecutionTask != null;
    }

    /**
     * Returns whether the comment has been edited.
     *
     * @return true if edited
     */
    public boolean hasBeenEdited() {
        return editedAt != null;
    }

    /**
     * Returns whether this comment contains any text.
     *
     * @return true if comment text exists
     */
    public boolean hasCommentText() {
        return commentText != null
                && !commentText.isBlank();
    }

    @Override
    public String toString() {

        return "ApprovalExecutionComment{" +
                "id=" + getId() +
                ", commentReference='" + commentReference + '\'' +
                ", commentType=" + commentType +
                ", visibility=" + visibility +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", systemComment=" + systemComment +
                ", edited=" + edited +
                ", active=" + isActive() +
                '}';
    }
}
