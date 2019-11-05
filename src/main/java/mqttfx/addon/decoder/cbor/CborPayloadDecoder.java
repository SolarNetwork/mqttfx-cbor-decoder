/* ========================================================================
 * Copyright 2019 Matt Magoffin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */

package mqttfx.addon.decoder.cbor;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import de.jensd.addon.decoder.AbstractPayloadDecoder;
import de.jensd.addon.decoder.PayloadDecoder;

/**
 * {@link PayloadDecoder} for CBOR messages.
 * 
 * @author matt
 */
public class CborPayloadDecoder extends AbstractPayloadDecoder {

  private final ObjectMapper cborMapper;
  private final ObjectMapper jsonMapper;

  /**
   * Constructor.
   */
  public CborPayloadDecoder() {
    super();
    cborMapper = new ObjectMapper(new CBORFactory());
    jsonMapper = new ObjectMapper();
  }

  @Override
  public String getName() {
    return "CBOR decoder";
  }

  @Override
  public String getId() {
    return "cbor_decoder";
  }

  @Override
  public String getVersion() {
    return "1.0";
  }

  @Override
  public String getDescription() {
    return "Decode CBOR into pretty-printed JSON.";
  }

  @Override
  public String decode(byte[] payload) {
    String result;
    try {
      Object json = cborMapper.readValue(payload, Object.class);
      result = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    } catch (IOException ex) {
      result = "*** PAYLOAD IS NOT VALID JSON DATA *** \n\n" + ex.getMessage();
    }
    return result;
  }

}
