/*
 * This file is part of FragPipe.
 *
 * FragPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FragPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FragPipe.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dmtavt.fragpipe.tools.msbooster;

import com.github.chhh.utils.SwingUtils;
import com.github.chhh.utils.swing.JPanelBase;
import com.github.chhh.utils.swing.MigUtils;
import com.github.chhh.utils.swing.UiCheck;
import java.awt.Component;
import java.awt.ItemSelectable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MSBoosterPanel extends JPanelBase {
  private static final Logger log = LoggerFactory.getLogger(MSBoosterPanel.class);
  private static final String PREFIX = "msbooster.";
  private JPanel pTop;
  private JPanel pContent;
  private static final MigUtils mu = MigUtils.get();
  private UiCheck checkRun;
  private UiCheck uiCheckPredictRT;
  private UiCheck uiCheckPredictSpectra;
  private UiCheck uiUseCorrelatedFeatures;

  public MSBoosterPanel() {
    super();
  }

  @Override
  protected ItemSelectable getRunCheckbox() {
    return checkRun;
  }

  @Override
  protected Component getEnablementToggleComponent() {
    return pContent;
  }

  @Override
  protected String getComponentNamePrefix() {
    return PREFIX;
  }

  public void setRunStatus(boolean status) {
    checkRun.setSelected(status);
  }

  @Override
  protected void init() {
    mu.layout(this, mu.lcFillXNoInsetsTopBottom());
    mu.border(this, "Rescoring Using Deep Learning Prediction");

    pTop = createPanelTop();

    uiCheckPredictRT = new UiCheck("Predict RT", null, true);
    uiCheckPredictRT.setName("predict-rt");

    uiCheckPredictSpectra = new UiCheck("Predict spectra", null, true);
    uiCheckPredictSpectra.setName("predict-spectra");

    uiUseCorrelatedFeatures = new UiCheck("Use correlated features", null, false);
    uiUseCorrelatedFeatures.setName("use-correlated-features");

    pContent = mu.newPanel(null, mu.lcFillXNoInsetsTopBottom());
    mu.add(pContent, uiCheckPredictRT).split();
    mu.add(pContent, uiCheckPredictSpectra);
    mu.add(pContent, uiUseCorrelatedFeatures);

    mu.add(this, pTop).growX().wrap();
    mu.add(this, pContent).growX().wrap();
  }

  private JPanel createPanelTop() {

    JPanel p = mu.newPanel(null, mu.lcFillXNoInsetsTopBottom());

    checkRun = new UiCheck("Run MSBooster", null, false);
    checkRun.setName("run-msbooster");
    JLabel info = new JLabel("<html>Rescoring using deep learning prediction. Require <b>Run Percolator</b> in PSM validation panel.");

    mu.add(p, checkRun).split();
    mu.add(p, info).gapLeft("80px").wrap();
    return p;
  }

  public boolean isRun() {
    return SwingUtils.isEnabledAndChecked(checkRun);
  }

  public boolean predictRt() {
    return uiCheckPredictRT.isSelected();
  }

  public boolean predictSpectra() {
    return uiCheckPredictSpectra.isSelected();
  }

  public boolean useCorrelatedFeatures() {
    return uiUseCorrelatedFeatures.isSelected();
  }

  @Override
  public void initMore() {
    updateEnabledStatus(this, true);
    super.initMore();
  }
}
