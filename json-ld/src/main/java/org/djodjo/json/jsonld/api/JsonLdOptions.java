/*
 * Copyright (C) 2015 Kalin Maldzhanski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package org.djodjo.json.jsonld.api;

/**
 * The JsonLdOptions type as specified in the <a
 * href="http://www.w3.org/TR/json-ld-api/#the-jsonldoptions-type">JSON-LD-API
 * specification</a>.
 */
public class JsonLdOptions {
    /**
     * Constructs an instance of JsonLdOptions using an empty base.
     */
    public JsonLdOptions() {
        this("");
    }

    /**
     * Constructs an instance of JsonLdOptions using the given base.
     *
     * @param base
     *            The base IRI for the document.
     */
    public JsonLdOptions(String base) {
        this.setBase(base);
    }

    // Base options : http://www.w3.org/TR/json-ld-api/#idl-def-JsonLdOptions

    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-base
     */
    private String base = null;

    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-compactArrays
     */
    private Boolean compactArrays = true;
    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-expandContext
     */
    private Object expandContext = null;
    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-processingMode
     */
    private String processingMode = "json-ld-1.0";
    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-documentLoader
     */
    private LoadDocumentCallback documentLoader = null;

    // Frame options : http://json-ld.org/spec/latest/json-ld-framing/

    private Boolean embed = null;
    private Boolean explicit = null;
    private Boolean omitDefault = null;

    // RDF conversion options :
    // http://www.w3.org/TR/json-ld-api/#serialize-rdf-as-json-ld-algorithm

    Boolean useRdfType = false;
    Boolean useNativeTypes = false;
    private boolean produceGeneralizedRdf = false;

    public Boolean getEmbed() {
        return embed;
    }

    public void setEmbed(Boolean embed) {
        this.embed = embed;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public Boolean getOmitDefault() {
        return omitDefault;
    }

    public void setOmitDefault(Boolean omitDefault) {
        this.omitDefault = omitDefault;
    }

    public Boolean getCompactArrays() {
        return compactArrays;
    }

    public void setCompactArrays(Boolean compactArrays) {
        this.compactArrays = compactArrays;
    }

    public Object getExpandContext() {
        return expandContext;
    }

    public void setExpandContext(Object expandContext) {
        this.expandContext = expandContext;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Boolean getUseRdfType() {
        return useRdfType;
    }

    public void setUseRdfType(Boolean useRdfType) {
        this.useRdfType = useRdfType;
    }

    public Boolean getUseNativeTypes() {
        return useNativeTypes;
    }

    public void setUseNativeTypes(Boolean useNativeTypes) {
        this.useNativeTypes = useNativeTypes;
    }

    public boolean getProduceGeneralizedRdf() {
        return this.produceGeneralizedRdf;
    }

    public void setProduceGeneralizedRdf(Boolean produceGeneralizedRdf) {
        this.produceGeneralizedRdf = produceGeneralizedRdf;
    }

    public LoadDocumentCallback getDocumentLoader() {
        return documentLoader;
    }

    public void setDocumentLoader(LoadDocumentCallback documentLoader) {
        this.documentLoader = documentLoader;
    }

    // REMOVE IT WHEN DONE
    public String format = null;
    public Boolean useNamespaces = false;
    public String outputForm = null;
    
}
