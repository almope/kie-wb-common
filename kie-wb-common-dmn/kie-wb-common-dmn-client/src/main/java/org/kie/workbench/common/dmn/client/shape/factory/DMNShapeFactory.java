/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.dmn.client.shape.factory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.kie.workbench.common.dmn.api.definition.DMNDefinition;
import org.kie.workbench.common.dmn.api.definition.v1_1.Association;
import org.kie.workbench.common.dmn.api.definition.v1_1.AuthorityRequirement;
import org.kie.workbench.common.dmn.api.definition.v1_1.BusinessKnowledgeModel;
import org.kie.workbench.common.dmn.api.definition.v1_1.DMNDiagram;
import org.kie.workbench.common.dmn.api.definition.v1_1.Decision;
import org.kie.workbench.common.dmn.api.definition.v1_1.InformationRequirement;
import org.kie.workbench.common.dmn.api.definition.v1_1.InputData;
import org.kie.workbench.common.dmn.api.definition.v1_1.KnowledgeRequirement;
import org.kie.workbench.common.dmn.api.definition.v1_1.KnowledgeSource;
import org.kie.workbench.common.dmn.api.definition.v1_1.TextAnnotation;
import org.kie.workbench.common.dmn.client.shape.def.AssociationShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.AuthorityRequirementShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.BusinessKnowledgeModelShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.DMNDiagramShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.DecisionShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.InformationRequirementShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.InputDataShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.KnowledgeRequirementShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.KnowledgeSourceShapeDef;
import org.kie.workbench.common.dmn.client.shape.def.TextAnnotationShapeDef;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.factory.DelegateShapeFactory;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.definition.shape.Glyph;
import org.kie.workbench.common.stunner.svg.client.shape.factory.SVGShapeFactory;

@Dependent
public class DMNShapeFactory implements ShapeFactory<DMNDefinition, Shape> {

    private final SVGShapeFactory svgShapeFactory;
    private final DMNConnectorShapeFactory dmnConnectorShapeFactory;
    private final DelegateShapeFactory<DMNDefinition, Shape> delegateShapeFactory;

    @Inject
    public DMNShapeFactory(final SVGShapeFactory svgShapeFactory,
                           final DMNConnectorShapeFactory dmnConnectorShapeFactory,
                           final DelegateShapeFactory<DMNDefinition, Shape> delegateShapeFactory) {
        this.svgShapeFactory = svgShapeFactory;
        this.dmnConnectorShapeFactory = dmnConnectorShapeFactory;
        this.delegateShapeFactory = delegateShapeFactory;
    }

    @PostConstruct
    public void init() {
        delegateShapeFactory
                .delegate(DMNDiagram.class,
                          new DMNDiagramShapeDef(),
                          () -> svgShapeFactory)
                .delegate(InputData.class,
                          new InputDataShapeDef(),
                          () -> svgShapeFactory)
                .delegate(KnowledgeSource.class,
                          new KnowledgeSourceShapeDef(),
                          () -> svgShapeFactory)
                .delegate(BusinessKnowledgeModel.class,
                          new BusinessKnowledgeModelShapeDef(),
                          () -> svgShapeFactory)
                .delegate(Decision.class,
                          new DecisionShapeDef(),
                          () -> svgShapeFactory)
                .delegate(TextAnnotation.class,
                          new TextAnnotationShapeDef(),
                          () -> svgShapeFactory)
                .delegate(Association.class,
                          new AssociationShapeDef(),
                          () -> dmnConnectorShapeFactory)
                .delegate(InformationRequirement.class,
                          new InformationRequirementShapeDef(),
                          () -> dmnConnectorShapeFactory)
                .delegate(KnowledgeRequirement.class,
                          new KnowledgeRequirementShapeDef(),
                          () -> dmnConnectorShapeFactory)
                .delegate(AuthorityRequirement.class,
                          new AuthorityRequirementShapeDef(),
                          () -> dmnConnectorShapeFactory);
    }

    @Override
    public Shape newShape(final DMNDefinition definition) {
        return delegateShapeFactory.newShape(definition);
    }

    @Override
    public Glyph getGlyph(final String definitionId) {
        return delegateShapeFactory.getGlyph(definitionId);
    }
}