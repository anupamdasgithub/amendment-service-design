/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.bpmn2;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jbpm.util.JsonSchemaUtil;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.process.WorkItem;
import org.kie.kogito.process.ProcessService;
import org.kie.kogito.process.workitem.Attachment;
import org.kie.kogito.process.workitem.AttachmentInfo;
import org.kie.kogito.process.workitem.Comment;
import org.kie.kogito.process.workitem.Policies;
import org.kie.kogito.process.workitem.TaskModel;
import org.kie.kogito.auth.IdentityProvider;
import org.kie.kogito.auth.IdentityProviders;
import org.kie.kogito.auth.SecurityPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/amendment_joint_to_sole")
@org.springframework.stereotype.Component()
public class Amendment_joint_to_soleResource {

    @org.springframework.beans.factory.annotation.Autowired()
    @org.springframework.beans.factory.annotation.Qualifier("amendment_joint_to_sole")
    Process<Amendment_joint_to_soleModel> process;

    @Autowired
    ProcessService processService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public ResponseEntity<Amendment_joint_to_soleModelOutput> createResource_amendment_joint_to_sole(@RequestHeader HttpHeaders httpHeaders, @RequestParam(value = "businessKey", required = false) String businessKey, @RequestBody(required = false) Amendment_joint_to_soleModelInput resource, UriComponentsBuilder uriComponentsBuilder) {
        ProcessInstance<Amendment_joint_to_soleModel> pi = processService.createProcessInstance(process, businessKey, Optional.ofNullable(resource).orElse(new Amendment_joint_to_soleModelInput()).toModel(), httpHeaders, httpHeaders.getOrEmpty("X-KOGITO-StartFromNode").stream().findFirst().orElse(null));
        return ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}").buildAndExpand(pi.id()).toUri()).body(pi.checkError().variables().toModel());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public List<Amendment_joint_to_soleModelOutput> getResources_amendment_joint_to_sole() {
        return processService.getProcessInstanceOutput(process);
    }

    @GetMapping(value = "/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public Map<String, Object> getResourceSchema_amendment_joint_to_sole() {
        return JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public Amendment_joint_to_soleModelOutput getResource_amendment_joint_to_sole(@PathVariable("id") String id) {
        return processService.findById(process, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public Amendment_joint_to_soleModelOutput deleteResource_amendment_joint_to_sole(@PathVariable("id") final String id) {
        return processService.delete(process, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public Amendment_joint_to_soleModelOutput updateModel_amendment_joint_to_sole(@PathVariable("id") String id, @RequestBody(required = false) Amendment_joint_to_soleModelInput resource) {
        return processService.update(process, id, resource.toModel()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public Amendment_joint_to_soleModelOutput updateModelPartial_amendment_joint_to_sole(@PathVariable("id") String id, @RequestBody(required = false) Amendment_joint_to_soleModelInput resource) {
        return processService.updatePartial(process, id, resource.toModel()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "amendment_joint_to_sole", description = "")
    public List<TaskModel> getTasks_amendment_joint_to_sole(@PathVariable("id") String id, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getTasks(process, id, SecurityPolicy.of(IdentityProviders.of(user, groups))).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).stream().map(org.drools.bpmn2.Amendment_joint_to_sole_TaskModelFactory::from).collect(Collectors.toList());
    }

    @PostMapping(value = "/{id}/Core_failure", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput signal_0(@PathVariable("id") final String id) {
        return processService.signalProcessInstance(process, id, null, "Core failure").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Party_disputes_consent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput signal_1(@PathVariable("id") final String id) {
        return processService.signalProcessInstance(process, id, null, "Party disputes consent").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Underwriting_review/{taskId}/phases/{phase}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput completeTask_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("phase") final String phase, @RequestParam("user") final String user, @RequestParam("group") final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_7_TaskOutput model) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), model).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Underwriting_review/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public org.drools.bpmn2.Amendment_joint_to_sole_7_TaskOutput saveTask_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_7_TaskOutput model) {
        return processService.saveTask(process, id, taskId, SecurityPolicy.of(user, groups), model, org.drools.bpmn2.Amendment_joint_to_sole_7_TaskOutput::fromMap).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Underwriting_review/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput taskTransition_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "phase", required = false, defaultValue = "complete") final String phase, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_7_TaskOutput model) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), model).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Underwriting_review/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public org.drools.bpmn2.Amendment_joint_to_sole_7_TaskModel getTask_Underwriting_review_2(@PathVariable("id") String id, @PathVariable("taskId") String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getTask(process, id, taskId, SecurityPolicy.of(user, groups), org.drools.bpmn2.Amendment_joint_to_sole_7_TaskModel::from).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Underwriting_review/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput abortTask_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "phase", required = false, defaultValue = "abort") final String phase, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), null).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "Underwriting_review/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSchema_Underwriting_review_2() {
        return JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Underwriting_review");
    }

    @GetMapping(value = "/{id}/Underwriting_review/{taskId}/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSchemaAndPhases_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getSchemaAndPhases(process, id, taskId, "Underwriting_review", SecurityPolicy.of(user, groups));
    }

    @PostMapping(value = "/{id}/Underwriting_review/{taskId}/comments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Comment> addComment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody String commentInfo, UriComponentsBuilder uriComponentsBuilder) {
        return processService.addComment(process, id, taskId, SecurityPolicy.of(user, groups), commentInfo).map(comment -> ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}/Underwriting_review/{taskId}/comments/{commentId}").buildAndExpand(id, taskId, comment.getId().toString()).toUri()).body(comment)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Underwriting_review/{taskId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public Comment updateComment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody String comment) {
        return processService.updateComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups), comment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Underwriting_review/{taskId}/comments/{commentId}")
    public ResponseEntity deleteComment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.deleteComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups)).map(removed -> (removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Underwriting_review/{taskId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attachment> addAttachment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody AttachmentInfo attachmentInfo, UriComponentsBuilder uriComponentsBuilder) {
        return processService.addAttachment(process, id, taskId, SecurityPolicy.of(user, groups), attachmentInfo).map(attachment -> ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}/Underwriting_review/{taskId}/attachments/{attachmentId}").buildAndExpand(id, taskId, attachment.getId()).toUri()).body(attachment)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Underwriting_review/{taskId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Attachment updateAttachment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody AttachmentInfo attachment) {
        return processService.updateAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups), attachment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Underwriting_review/{taskId}/attachments/{attachmentId}")
    public ResponseEntity deleteAttachment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.deleteAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups)).map(removed -> (removed ? ResponseEntity.ok() : ResponseEntity.notFound()).build()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Underwriting_review/{taskId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Attachment getAttachment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment " + attachmentId + " not found"));
    }

    @GetMapping(value = "/{id}/Underwriting_review/{taskId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Attachment> getAttachments_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user") final String user, @RequestParam(value = "group") final List<String> groups) {
        return processService.getAttachments(process, id, taskId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Underwriting_review/{taskId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment getComment_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment " + commentId + " not found"));
    }

    @GetMapping(value = "/{id}/Underwriting_review/{taskId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Comment> getComments_Underwriting_review_2(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getComments(process, id, taskId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Maker_checker_approval/{taskId}/phases/{phase}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput completeTask_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("phase") final String phase, @RequestParam("user") final String user, @RequestParam("group") final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_21_TaskOutput model) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), model).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Maker_checker_approval/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public org.drools.bpmn2.Amendment_joint_to_sole_21_TaskOutput saveTask_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_21_TaskOutput model) {
        return processService.saveTask(process, id, taskId, SecurityPolicy.of(user, groups), model, org.drools.bpmn2.Amendment_joint_to_sole_21_TaskOutput::fromMap).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Maker_checker_approval/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput taskTransition_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "phase", required = false, defaultValue = "complete") final String phase, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_21_TaskOutput model) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), model).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Maker_checker_approval/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public org.drools.bpmn2.Amendment_joint_to_sole_21_TaskModel getTask_Maker_checker_approval_3(@PathVariable("id") String id, @PathVariable("taskId") String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getTask(process, id, taskId, SecurityPolicy.of(user, groups), org.drools.bpmn2.Amendment_joint_to_sole_21_TaskModel::from).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Maker_checker_approval/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput abortTask_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "phase", required = false, defaultValue = "abort") final String phase, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), null).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "Maker_checker_approval/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSchema_Maker_checker_approval_3() {
        return JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Maker_checker_approval");
    }

    @GetMapping(value = "/{id}/Maker_checker_approval/{taskId}/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSchemaAndPhases_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getSchemaAndPhases(process, id, taskId, "Maker_checker_approval", SecurityPolicy.of(user, groups));
    }

    @PostMapping(value = "/{id}/Maker_checker_approval/{taskId}/comments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Comment> addComment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody String commentInfo, UriComponentsBuilder uriComponentsBuilder) {
        return processService.addComment(process, id, taskId, SecurityPolicy.of(user, groups), commentInfo).map(comment -> ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}/Maker_checker_approval/{taskId}/comments/{commentId}").buildAndExpand(id, taskId, comment.getId().toString()).toUri()).body(comment)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Maker_checker_approval/{taskId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public Comment updateComment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody String comment) {
        return processService.updateComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups), comment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Maker_checker_approval/{taskId}/comments/{commentId}")
    public ResponseEntity deleteComment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.deleteComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups)).map(removed -> (removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Maker_checker_approval/{taskId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attachment> addAttachment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody AttachmentInfo attachmentInfo, UriComponentsBuilder uriComponentsBuilder) {
        return processService.addAttachment(process, id, taskId, SecurityPolicy.of(user, groups), attachmentInfo).map(attachment -> ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}/Maker_checker_approval/{taskId}/attachments/{attachmentId}").buildAndExpand(id, taskId, attachment.getId()).toUri()).body(attachment)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Maker_checker_approval/{taskId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Attachment updateAttachment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody AttachmentInfo attachment) {
        return processService.updateAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups), attachment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Maker_checker_approval/{taskId}/attachments/{attachmentId}")
    public ResponseEntity deleteAttachment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.deleteAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups)).map(removed -> (removed ? ResponseEntity.ok() : ResponseEntity.notFound()).build()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Maker_checker_approval/{taskId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Attachment getAttachment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment " + attachmentId + " not found"));
    }

    @GetMapping(value = "/{id}/Maker_checker_approval/{taskId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Attachment> getAttachments_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user") final String user, @RequestParam(value = "group") final List<String> groups) {
        return processService.getAttachments(process, id, taskId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Maker_checker_approval/{taskId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment getComment_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment " + commentId + " not found"));
    }

    @GetMapping(value = "/{id}/Maker_checker_approval/{taskId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Comment> getComments_Maker_checker_approval_3(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getComments(process, id, taskId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/phases/{phase}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput completeTask_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("phase") final String phase, @RequestParam("user") final String user, @RequestParam("group") final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_29_TaskOutput model) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), model).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Investigate_consent_dispute/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public org.drools.bpmn2.Amendment_joint_to_sole_29_TaskOutput saveTask_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_29_TaskOutput model) {
        return processService.saveTask(process, id, taskId, SecurityPolicy.of(user, groups), model, org.drools.bpmn2.Amendment_joint_to_sole_29_TaskOutput::fromMap).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Investigate_consent_dispute/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput taskTransition_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "phase", required = false, defaultValue = "complete") final String phase, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody(required = false) final org.drools.bpmn2.Amendment_joint_to_sole_29_TaskOutput model) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), model).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Investigate_consent_dispute/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public org.drools.bpmn2.Amendment_joint_to_sole_29_TaskModel getTask_Investigate_consent_dispute_4(@PathVariable("id") String id, @PathVariable("taskId") String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getTask(process, id, taskId, SecurityPolicy.of(user, groups), org.drools.bpmn2.Amendment_joint_to_sole_29_TaskModel::from).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Investigate_consent_dispute/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Amendment_joint_to_soleModelOutput abortTask_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "phase", required = false, defaultValue = "abort") final String phase, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.taskTransition(process, id, taskId, phase, SecurityPolicy.of(user, groups), null).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "Investigate_consent_dispute/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSchema_Investigate_consent_dispute_4() {
        return JsonSchemaUtil.load(this.getClass().getClassLoader(), process.id(), "Investigate_consent_dispute");
    }

    @GetMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/schema", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getSchemaAndPhases_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getSchemaAndPhases(process, id, taskId, "Investigate_consent_dispute", SecurityPolicy.of(user, groups));
    }

    @PostMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/comments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Comment> addComment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody String commentInfo, UriComponentsBuilder uriComponentsBuilder) {
        return processService.addComment(process, id, taskId, SecurityPolicy.of(user, groups), commentInfo).map(comment -> ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}/Investigate_consent_dispute/{taskId}/comments/{commentId}").buildAndExpand(id, taskId, comment.getId().toString()).toUri()).body(comment)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public Comment updateComment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody String comment) {
        return processService.updateComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups), comment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/comments/{commentId}")
    public ResponseEntity deleteComment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.deleteComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups)).map(removed -> (removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attachment> addAttachment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody AttachmentInfo attachmentInfo, UriComponentsBuilder uriComponentsBuilder) {
        return processService.addAttachment(process, id, taskId, SecurityPolicy.of(user, groups), attachmentInfo).map(attachment -> ResponseEntity.created(uriComponentsBuilder.path("/amendment_joint_to_sole/{id}/Investigate_consent_dispute/{taskId}/attachments/{attachmentId}").buildAndExpand(id, taskId, attachment.getId()).toUri()).body(attachment)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Attachment updateAttachment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups, @RequestBody AttachmentInfo attachment) {
        return processService.updateAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups), attachment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/attachments/{attachmentId}")
    public ResponseEntity deleteAttachment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.deleteAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups)).map(removed -> (removed ? ResponseEntity.ok() : ResponseEntity.notFound()).build()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Attachment getAttachment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("attachmentId") final String attachmentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getAttachment(process, id, taskId, attachmentId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment " + attachmentId + " not found"));
    }

    @GetMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Attachment> getAttachments_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user") final String user, @RequestParam(value = "group") final List<String> groups) {
        return processService.getAttachments(process, id, taskId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment getComment_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @PathVariable("commentId") final String commentId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getComment(process, id, taskId, commentId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment " + commentId + " not found"));
    }

    @GetMapping(value = "/{id}/Investigate_consent_dispute/{taskId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Comment> getComments_Investigate_consent_dispute_4(@PathVariable("id") final String id, @PathVariable("taskId") final String taskId, @RequestParam(value = "user", required = false) final String user, @RequestParam(value = "group", required = false) final List<String> groups) {
        return processService.getComments(process, id, taskId, SecurityPolicy.of(user, groups)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
