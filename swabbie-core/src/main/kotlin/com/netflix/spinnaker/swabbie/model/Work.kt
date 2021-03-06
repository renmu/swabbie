/*
 * Copyright 2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package com.netflix.spinnaker.swabbie.model

import com.netflix.spinnaker.config.Exclusion
import com.netflix.spinnaker.swabbie.Excludable
import com.netflix.spinnaker.swabbie.ExclusionPolicy

data class Work(
  val namespace: String,
  val configuration: WorkConfiguration
)

data class WorkConfiguration(
  val namespace: String,
  val account: Account,
  val location: String,
  val cloudProvider: String,
  val resourceType: String,
  val retentionDays: Int,
  val exclusions: List<Exclusion>,
  val dryRun: Boolean = true,
  override val name: String = namespace
): Excludable {
  override fun shouldBeExcluded(exclusionPolicies: List<ExclusionPolicy>, exclusions: List<Exclusion>): Boolean =
    exclusionPolicies.find { it.apply(this, exclusions) } != null
}
