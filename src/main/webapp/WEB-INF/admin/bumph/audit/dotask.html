	#include("/common/include/pageTitleBread.html")
	<div id="page-content">
			<div class="col-lg-12">
		        <div class="panel">
		            <div class="panel-heading">
		                <h3 class="panel-title">
		                	公文起草
		                	<a href="#(ctx)/processEditor/diagram-viewer/index.html?processDefinitionId=#(task?task.DEFID:'')&processInstanceId=#(o?o.proc_ins_id:'')" target="_blank" style="position: absolute;right: 10px">
		                		<button class="btn btn-mint" type="button">流程</button>
		                	</a>
		                </h3>
		            </div>
		            <form id="editForm" class="panel-body form-horizontal form-padding">
		            	<input type="hidden" name="oaBumph.id" value="#(o?o.id:'')">
		            	<input type="hidden" name="taskid" value="#(task?task.TASKID:'')">
		            	<div class="form-group">
		                    <label class="col-md-2 control-label" for="demo-text-input">公文文号</label>
		                    <div class="col-md-3" style="padding-left: 15px;">
				                    <select class="selectpicker" disabled>
		                                <option value="0">呈批文</option>
		                            </select>
		                    </div>
		                    <div class="col-md-3">
			                    <input type="text" value="#(o.doc_num_year)"  class="form-control" readonly>
		                    </div>
		                    <div class="col-md-1">
			                    <input type="text" value="2" class="form-control" readonly>
		                    </div>
		                    <div class="col-md-1" style="line-height: 32px;">
			                    	号文
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label class="col-md-2 control-label" for="demo-text-input">起草部门</label>
		                    <div class="col-md-3">
		                        <input type="text" value="#(o.sender_orgname)" readonly class="form-control">
		                    </div>
		                    <div class="col-md-3">
		                        <input type="text" value="#(o.sender_name)" readonly class="form-control">
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label class="col-md-2 control-label" for="demo-text-input">标题</label>
		                    <div class="col-md-8">
		                        <input type="text" class="form-control"  value="#(o?o.title:'')" readonly />
		                        <small class="help-block">请输入公文标题</small>
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label class="col-md-2 control-label" for="demo-textarea-input">主送</label>
		                    <div class="col-md-8" style="line-height: 30px;">
		                        <input type="text"   id="firstOrgName" value="#(firstOrgName)" readonly class="form-control">
		                        <small class="help-block">请输选择主送单位</small>
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label class="col-md-2 control-label" for="demo-text-input">抄送</label>
		                    <div class="col-md-8" style="line-height: 30px;">
		                        <input type="text"   id="secondOrgName" value="#(secondOrgName)" readonly class="form-control">
		                        <small class="help-block">请输选择抄送单位</small>
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label class="col-md-2 control-label" for="demo-text-input">公文正文</label>
		                    <div class="col-md-8">
		                        <textarea rows="12" class="form-control" readonly >#(o?o.content:'')</textarea>
		                        <small class="help-block">请输入公文正文</small>
		                    </div>
		                </div>
		                
		                <div class="form-group">
			                    <label class="col-md-2 control-label" for="demo-text-input">部门领导批示信息</label>
			                    <div class="col-md-8">
			                        <textarea name="oaBumph.first_leader_audit" rows="8" class="form-control" #if(task.TASKDEFKEY!="leader1") disabled #end>#(o?o.first_leader_audit:'')</textarea>
			                    </div>
			            </div>
			            <div class="form-group">
			                    <label class="col-md-2 control-label" for="demo-text-input">公司领导批示信息</label>
			                    <div class="col-md-8">
			                        <textarea name="oaBumph.second_leader_audit" rows="8" class="form-control" #if(task.TASKDEFKEY!="audit") disabled #end>#(o?o.second_leader_audit:'')</textarea>
			                    </div>
			            </div>
			            <div class="form-group">
			                    <label class="col-md-2 control-label" for="demo-text-input">办理意见</label>
			                    <div class="col-md-8">
			                        <textarea name="comment" rows="8" class="form-control"></textarea>
			                    </div>
			            </div>
		                
		                #if(view!="detail") 
			                <div class="panel-footer">
			                    <div class="row">
			                        <div class="col-sm-9 col-sm-offset-3">
			                            #if(task.TASKDEFKEY=="audit"||task.TASKDEFKEY=="leader1")
				                            <button class="btn btn-warning" type="reset">重置</button>
				                            <button class="btn btn-danger" type="button" onclick="completeTask(0)">不同意</button>
				                            <button class="btn btn-mint" type="button" onclick="completeTask(1)">同意</button>
				                        #else
				                        	<button class="btn btn-mint" type="button" onclick="completeTask(1)">提交</button>
				                        #end
			                        </div>
			                    </div>
			                </div>
			            #end
		            </form>
		        </div>
		    </div>
	</div>
	<script>
		function formValidate(){
			$('#editForm').bootstrapValidator({
	    		excluded:[":disabled"],
	            fields: {
	            	"oaBumph.first_leader_audit": {
	                    validators: {
	                        notEmpty: {
	                            message: '*请填写领导批示信息'
	                        }
	                    }
	                },
	                "oaBumph.second_leader_audit": {
	                    validators: {
	                        notEmpty: {
	                            message: '*请填写领导批示信息'
	                        }
	                    }
	                }
	            }
	        }).bootstrapValidator('validate');
		}
		function save(pass){
			var data = common_ajax.ajaxFunc("/admin/bumph/bumphSubmit?pass="+pass, $('#editForm').serialize(), "json", null);
			if(data.success){
				pointLion.alertMsg(data.message , "success" , "small" , function(){
					doPjax(ctx+'/admin/bumph/getToDoPage');//跳转到列表页
				});
			}else{
				pointLion.alertMsg(data.message , "danger" , "small" , null);
			}
		}
		function completeTask(pass){
			var validator = formValidate();
			var va = $("#editForm").data('bootstrapValidator').isValid();
			if(va){
				save(pass);
			}
		}
		$(document).ready(function() {
			$(".selectpicker").selectpicker({});
			$(".bootstrap-select").attr("style","width:100%;");
			//formValidate();
		});
	</script>