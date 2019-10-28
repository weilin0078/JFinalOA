/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pointlion.mvc.admin.oa.workflow.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.pointlion.plugin.flowable.FlowablePlugin;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.constants.ModelDataJsonConstants;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;

/**
 * 流程编辑器编辑
 */
public class ModelEditorJsonRestResource extends Controller implements ModelDataJsonConstants{

  private static final Log LOG = Log.getLog(ModelEditorJsonRestResource.class);
  
  private ObjectMapper objectMapper = new ObjectMapper();
  
//  @RequestMapping(value="/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
  public void getEditorJson2() {
	RepositoryService repositoryService = FlowablePlugin.buildProcessEngine().getRepositoryService();
    ObjectNode modelNode = null;
    String modelId = getPara("modelId");
    Model model = repositoryService.getModel(modelId);
    if (model != null) {
      try {
        if (StringUtils.isNotEmpty(model.getMetaInfo())) {
          modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
        } else {
          modelNode = objectMapper.createObjectNode();
          modelNode.put(MODEL_NAME, model.getName());
        }
        modelNode.put(MODEL_ID, model.getId());
        ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
            new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
        modelNode.put("model", editorJsonNode);
        
      } catch (Exception e) {
        LOG.error("Error creating model JSON", e);
        throw new FlowableException("Error creating model JSON", e);
      }
    }
    renderJson(modelNode.toString());
  }
}
