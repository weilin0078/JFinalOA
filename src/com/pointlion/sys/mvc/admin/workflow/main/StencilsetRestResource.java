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
package com.pointlion.sys.mvc.admin.workflow.main;

import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;

import com.jfinal.core.Controller;

/**
 * @author Tijs Rademakers
 */
public class StencilsetRestResource extends Controller{
  
//  @RequestMapping(value="/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
  public void getStencilset() {
    InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("/stencilset.json");
    try {
      renderJson( IOUtils.toString(stencilsetStream, "utf-8"));
    } catch (Exception e) {
      throw new ActivitiException("Error while loading stencil set", e);
    }
  }
}
