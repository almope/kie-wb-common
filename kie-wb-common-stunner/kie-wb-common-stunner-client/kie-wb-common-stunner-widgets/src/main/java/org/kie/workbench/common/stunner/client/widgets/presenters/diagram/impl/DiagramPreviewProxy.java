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

package org.kie.workbench.common.stunner.client.widgets.presenters.diagram.impl;

import org.kie.workbench.common.stunner.client.widgets.views.WidgetWrapperView;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.api.ShapeManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandlerProxy;
import org.kie.workbench.common.stunner.core.client.canvas.command.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.select.SelectionControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.zoom.ZoomControl;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;

/**
 * An abstract DiagramViewer type that opens the diagram in a viewer which is scaled
 * to fit the given size, usually for previewing goals.
 * @param <D> The diagram type.
 */
public abstract class DiagramPreviewProxy<D extends Diagram> extends AbstractDiagramPreview<D, CanvasHandlerProxy> {

    private final DiagramViewerProxy<D> viewer;

    @SuppressWarnings("unchecked")
    public DiagramPreviewProxy(final DefinitionManager definitionManager,
                               final GraphUtils graphUtils,
                               final ShapeManager shapeManager,
                               final WidgetWrapperView view,
                               final CanvasCommandFactory canvasCommandFactory,
                               final SelectionControl<CanvasHandlerProxy, ?> selectionControl) {
        this.viewer =
                new DiagramViewerProxy<D>(definitionManager,
                                          graphUtils,
                                          shapeManager,
                                          view,
                                          canvasCommandFactory,
                                          selectionControl) {
                    @Override
                    public <C extends Canvas> ZoomControl<C> getZoomControl() {
                        return (ZoomControl<C>) DiagramPreviewProxy.this.getZoomControl();
                    }

                    @Override
                    protected AbstractCanvas getCanvas() {
                        return DiagramPreviewProxy.this.getCanvas();
                    }

                    @Override
                    protected void enableControls() {
                        DiagramPreviewProxy.this.enableControls();
                    }

                    @Override
                    protected void disableControls() {
                        DiagramPreviewProxy.this.disableControls();
                    }

                    @Override
                    protected void destroyControls() {
                        DiagramPreviewProxy.this.destroyControls();
                    }

                    @Override
                    protected AbstractCanvasHandler<D, ?> getProxiedHandler() {
                        return DiagramPreviewProxy.this.getProxiedHandler();
                    }
                };
    }

    protected abstract AbstractCanvas getCanvas();

    protected abstract AbstractCanvasHandler<D, ?> getProxiedHandler();

    protected abstract void enableControls();

    protected abstract void disableControls();

    protected abstract void destroyControls();

    @Override
    public DiagramViewerProxy<D> getViewer() {
        return viewer;
    }

    @Override
    public SelectionControl<CanvasHandlerProxy, ?> getSelectionControl() {
        return getViewer().getSelectionControl();
    }
}