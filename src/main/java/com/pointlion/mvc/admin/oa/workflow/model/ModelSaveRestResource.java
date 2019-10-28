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
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.plugin.flowable.FlowablePlugin;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.constants.ModelDataJsonConstants;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 流程编辑器保存
 */
public class ModelSaveRestResource extends Controller implements ModelDataJsonConstants {

  private static final Log LOG = Log.getLog(ModelSaveRestResource.class);

  private ObjectMapper objectMapper = new ObjectMapper();
  
//  @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
//  @ResponseStatus(value = HttpStatus.OK)
  @Before(Tx.class)
  public void saveModel() {
    try {
      String modelId = getPara("modelId");
      RepositoryService repositoryService = FlowablePlugin.buildProcessEngine().getRepositoryService();
      Model model = repositoryService.getModel(modelId);
      
      ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
      
      modelJson.put(MODEL_NAME, getPara("name"));
      modelJson.put(MODEL_DESCRIPTION, getPara("description"));
      model.setMetaInfo(modelJson.toString());
      model.setName(getPara("name"));
      
      repositoryService.saveModel(model);
      repositoryService.addModelEditorSource(model.getId(), getPara("json_xml").getBytes("utf-8"));
      
      InputStream svgStream = new ByteArrayInputStream(getPara("svg_xml").getBytes("utf-8"));
      TranscoderInput input = new TranscoderInput(svgStream);
      
      PNGTranscoder transcoder = new PNGTranscoder();
      // Setup output
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      TranscoderOutput output = new TranscoderOutput(outStream);
      
      transcoder.transcode(input, output);
      final byte[] result = outStream.toByteArray();
      repositoryService.addModelEditorSourceExtra(model.getId(), result);
      outStream.close();
      renderNull();
    } catch (Exception e) {
      LOG.error("Error saving model", e);
      throw new FlowableException("Error saving model", e);
    }
  }
}
