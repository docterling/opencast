/**
 * Licensed to The Apereo Foundation under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 *
 * The Apereo Foundation licenses this file to you under the Educational
 * Community License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at:
 *
 *   http://opensource.org/licenses/ecl2.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package org.opencastproject.statistics.provider.random.provider;

import org.opencastproject.statistics.api.DataResolution;
import org.opencastproject.statistics.api.DateUtil;
import org.opencastproject.statistics.api.TimeSeries;
import org.opencastproject.statistics.api.TimeSeriesProvider;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class RandomStatisticsProvider implements TimeSeriesProvider {

  private static final Random random = new Random();

  @Override
  public TimeSeries getValues(String resourceId, Instant from, Instant to, DataResolution resolution, ZoneId zoneId) {
    final List<String> labels = DateUtil.getBuckets(from, to, resolution, zoneId).stream()
        .map(Instant::toString)
        .collect(Collectors.toList());
    final List<Double> values = labels.stream()
        .map(l -> random.nextInt(100))
        .map(Double::valueOf)
        .collect(Collectors.toList());
    return new TimeSeries(labels, values, values.stream().mapToDouble(v -> v).sum());
  }
}
