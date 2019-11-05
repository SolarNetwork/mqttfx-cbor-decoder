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

package mqttfx.addon.decoder.cbor.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

import mqttfx.addon.decoder.cbor.CborPayloadDecoder;

/**
 * Test cases for the {@link CborPayloadDecoder} class.
 * 
 * @author matt
 */
public class CborPayloadDecoderTests {

  private ObjectMapper cborMapper() {
    return new ObjectMapper(new CBORFactory());
  }

  @Test
  public void decodeCbor() throws Exception {
    // GIVEN
    ObjectMapper mapper = cborMapper();
    Map<String, Object> data = new LinkedHashMap<>(2);
    data.put("foo", "bar");
    data.put("bim", 123);
    byte[] cbor = mapper.writeValueAsBytes(data);

    CborPayloadDecoder decoder = new CborPayloadDecoder();

    // WHEN
    String result = decoder.decode(cbor);

    // THEN
    assertThat("CBOR decoded to pretty string", result,
        equalTo("{\n  \"foo\" : \"bar\",\n  \"bim\" : 123\n}"));
  }

}
