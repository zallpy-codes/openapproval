package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalCommentType;
import com.zallpy.openapproval.approval.enums.ApprovalCommentVisibility;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionComment}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution comments.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionCommentRepository extends
        JpaRepository<ApprovalExecutionComment, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionComment> {

    /**
     * Finds a comment by its business reference.
     *
     * @param commentReference comment reference
     * @return matching comment
     */
    Optional<ApprovalExecutionComment> findByCommentReference(
            String commentReference);

    /**
     * Returns whether a comment reference exists.
     *
     * @param commentReference comment reference
     * @return true if found
     */
    boolean existsByCommentReference(
            String commentReference);

    /**
     * Finds comments belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Finds comments belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds comments belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds comments by comment type.
     *
     * @param commentType comment type
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentType(
            ApprovalCommentType commentType);

    /**
     * Finds comments by visibility.
     *
     * @param visibility comment visibility
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByVisibility(
            ApprovalCommentVisibility visibility);

    /**
     * Finds comments created by the specified author.
     *
     * @param authorId author identifier
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByAuthorId(
            String authorId);

    /**
     * Finds comments by author email.
     *
     * @param authorEmail author email
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByAuthorEmail(
            String authorEmail);

    /**
     * Finds system-generated comments.
     *
     * @param systemComment system comment flag
     * @return matching comments
     */
    List<ApprovalExecutionComment> findBySystemComment(
            boolean systemComment);

    /**
     * Finds active comments.
     *
     * @param active active flag
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByActive(
            boolean active);

    /**
     * Finds comments created before the specified time.
     *
     * @param commentedAt comment timestamp
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentedAtBefore(
            LocalDateTime commentedAt);

    /**
     * Finds comments created after the specified time.
     *
     * @param commentedAt comment timestamp
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentedAtAfter(
            LocalDateTime commentedAt);

    /**
     * Finds comments ordered by comment date descending.
     *
     * @return ordered comments
     */
    List<ApprovalExecutionComment> findAllByOrderByCommentedAtDesc();

    /**
     * Finds comments ordered by comment date ascending.
     *
     * @return ordered comments
     */
    List<ApprovalExecutionComment> findAllByOrderByCommentedAtAsc();

    /**
     * Finds comments for an approval execution ordered by comment date.
     *
     * @param approvalExecution approval execution
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionOrderByCommentedAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds comments for an execution stage ordered by comment date.
     *
     * @param approvalExecutionStage execution stage
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionStageOrderByCommentedAtDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds comments for an execution task ordered by comment date.
     *
     * @param approvalExecutionTask execution task
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionTaskOrderByCommentedAtDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds comments created by the specified author ordered by date.
     *
     * @param authorId author identifier
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByAuthorIdOrderByCommentedAtDesc(
            String authorId);

    /**
     * Finds comments created by the specified author email ordered by date.
     *
     * @param authorEmail author email
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByAuthorEmailOrderByCommentedAtDesc(
            String authorEmail);

    /**
     * Finds comments of the specified type ordered by date.
     *
     * @param commentType comment type
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentTypeOrderByCommentedAtDesc(
            ApprovalCommentType commentType);

    /**
     * Finds comments with the specified visibility ordered by date.
     *
     * @param visibility comment visibility
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByVisibilityOrderByCommentedAtDesc(
            ApprovalCommentVisibility visibility);

    /**
     * Finds edited comments.
     *
     * @param edited edited flag
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByEdited(
            boolean edited);

    /**
     * Finds edited comments ordered by comment date.
     *
     * @param edited edited flag
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByEditedOrderByCommentedAtDesc(
            boolean edited);

    /**
     * Finds comments edited after the specified date.
     *
     * @param editedAt edit timestamp
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByEditedAtAfter(
            LocalDateTime editedAt);

    /**
     * Counts comments belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return number of comments
     */
    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Counts comments belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return number of comments
     */
    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Counts comments belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return number of comments
     */
    long countByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Counts comments created by the specified author.
     *
     * @param authorId author identifier
     * @return number of comments
     */
    long countByAuthorId(
            String authorId);

    /**
     * Counts comments of the specified type.
     *
     * @param commentType comment type
     * @return number of comments
     */
    long countByCommentType(
            ApprovalCommentType commentType);

    /**
     * Counts comments having the specified visibility.
     *
     * @param visibility comment visibility
     * @return number of comments
     */
    long countByVisibility(
            ApprovalCommentVisibility visibility);

    /**
     * Counts system-generated comments.
     *
     * @param systemComment system-generated flag
     * @return number of comments
     */
    long countBySystemComment(
            boolean systemComment);

    /**
     * Counts edited comments.
     *
     * @param edited edited flag
     * @return number of comments
     */
    long countByEdited(
            boolean edited);

    /**
     * Returns whether comments exist for an execution.
     *
     * @param approvalExecution approval execution
     * @return true if comments exist
     */
    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns whether comments exist for a stage.
     *
     * @param approvalExecutionStage execution stage
     * @return true if comments exist
     */
    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Returns whether comments exist for a task.
     *
     * @param approvalExecutionTask execution task
     * @return true if comments exist
     */
    boolean existsByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Returns whether comments exist for an author.
     *
     * @param authorId author identifier
     * @return true if comments exist
     */
    boolean existsByAuthorId(
            String authorId);

    /**
     * Finds the most recently created comment for an execution.
     *
     * @param approvalExecution approval execution
     * @return latest comment
     */
    Optional<ApprovalExecutionComment> findFirstByApprovalExecutionOrderByCommentedAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the oldest comment for an execution.
     *
     * @param approvalExecution approval execution
     * @return oldest comment
     */
    Optional<ApprovalExecutionComment> findFirstByApprovalExecutionOrderByCommentedAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest comment for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return latest comment
     */
    Optional<ApprovalExecutionComment> findFirstByApprovalExecutionStageOrderByCommentedAtDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds the latest comment for an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return latest comment
     */
    Optional<ApprovalExecutionComment> findFirstByApprovalExecutionTaskOrderByCommentedAtDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds the latest comment created by an author.
     *
     * @param authorId author identifier
     * @return latest comment
     */
    Optional<ApprovalExecutionComment> findFirstByAuthorIdOrderByCommentedAtDesc(
            String authorId);

    /**
     * Finds comments created between the specified dates.
     *
     * @param start start date
     * @param end   end date
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    /**
     * Finds comments edited between the specified dates.
     *
     * @param start start date
     * @param end   end date
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByEditedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    /**
     * Finds comments created by an author with the specified visibility.
     *
     * @param authorId   author identifier
     * @param visibility visibility
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByAuthorIdAndVisibility(
            String authorId,
            ApprovalCommentVisibility visibility);

    /**
     * Finds comments created by an author with the specified type.
     *
     * @param authorId    author identifier
     * @param commentType comment type
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByAuthorIdAndCommentType(
            String authorId,
            ApprovalCommentType commentType);

    /**
     * Finds comments belonging to an execution with the specified visibility.
     *
     * @param approvalExecution approval execution
     * @param visibility        visibility
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionAndVisibility(
            ApprovalExecution approvalExecution,
            ApprovalCommentVisibility visibility);

    /**
     * Finds comments belonging to an execution with the specified type.
     *
     * @param approvalExecution approval execution
     * @param commentType       comment type
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionAndCommentType(
            ApprovalExecution approvalExecution,
            ApprovalCommentType commentType);

    /**
     * Finds comments belonging to an execution that were edited.
     *
     * @param approvalExecution approval execution
     * @param edited            edited flag
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionAndEdited(
            ApprovalExecution approvalExecution,
            boolean edited);

    /**
     * Finds comments belonging to an execution that are
     * system-generated.
     *
     * @param approvalExecution approval execution
     * @param systemComment     system comment flag
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionAndSystemComment(
            ApprovalExecution approvalExecution,
            boolean systemComment);

    /**
     * Finds comments belonging to an execution ordered by author.
     *
     * @param approvalExecution approval execution
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionOrderByAuthorNameAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds comments ordered by author name.
     *
     * @return ordered comments
     */
    List<ApprovalExecutionComment> findAllByOrderByAuthorNameAsc();

    /**
     * Finds comments ordered by reference.
     *
     * @return ordered comments
     */
    List<ApprovalExecutionComment> findAllByOrderByCommentReferenceAsc();

    /**
     * Finds comments ordered by type then comment date.
     *
     * @return ordered comments
     */
    List<ApprovalExecutionComment> findAllByOrderByCommentTypeAscCommentedAtDesc();

    /**
     * Finds comments ordered by visibility then comment date.
     *
     * @return ordered comments
     */
    List<ApprovalExecutionComment> findAllByOrderByVisibilityAscCommentedAtDesc();

    /**
     * Finds comments by execution, visibility and comment type.
     *
     * @param approvalExecution approval execution
     * @param visibility        comment visibility
     * @param commentType       comment type
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByApprovalExecutionAndVisibilityAndCommentType(
            ApprovalExecution approvalExecution,
            ApprovalCommentVisibility visibility,
            ApprovalCommentType commentType);

    /**
     * Finds comments created after the specified date ordered by
     * comment date descending.
     *
     * @param commentedAt comment date
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentedAtAfterOrderByCommentedAtDesc(
            LocalDateTime commentedAt);

    /**
     * Finds comments created before the specified date ordered by
     * comment date descending.
     *
     * @param commentedAt comment date
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByCommentedAtBeforeOrderByCommentedAtDesc(
            LocalDateTime commentedAt);

    /**
     * Finds comments awaiting review (edited comments).
     *
     * @param edited     edited flag
     * @param visibility visibility
     * @return matching comments
     */
    List<ApprovalExecutionComment> findByEditedAndVisibility(
            boolean edited,
            ApprovalCommentVisibility visibility);

    /**
     * Finds system comments by type.
     *
     * @param systemComment system comment flag
     * @param commentType   comment type
     * @return matching comments
     */
    List<ApprovalExecutionComment> findBySystemCommentAndCommentType(
            boolean systemComment,
            ApprovalCommentType commentType);
}