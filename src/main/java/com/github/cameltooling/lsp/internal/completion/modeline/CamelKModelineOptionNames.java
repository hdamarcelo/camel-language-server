/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.lsp.internal.completion.modeline;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemTag;

import com.github.cameltooling.lsp.internal.completion.FilterPredicateUtils;

public class CamelKModelineOptionNames {
	
	private CamelKModelineOptionNames() {}

	private static final Map<String, String> OPTION_NAMES_WITH_DESCRIPTION;
	private static final List<CompletionItem> COMPLETION_ITEMS;
	public static final String OPTION_NAME_TRAIT = "trait";
	public static final String OPTION_NAME_DEPENDENCY = "dependency";
	public static final String OPTION_NAME_PROPERTY = "property";
	public static final String OPTION_NAME_PROPERTY_FILE = "property-file";
	public static final String OPTION_NAME_RESOURCE = "resource";
	public static final String OPTION_NAME_OPEN_API = "open-api";
	
	private static final String DESCRIPTION_SINCE_COMMUNITY_1_5_PROD_1_4 = "Option is available since Community 1.5 and Red Hat productized 1.4";
	
	static {
		OPTION_NAMES_WITH_DESCRIPTION = new HashMap<>();
		OPTION_NAMES_WITH_DESCRIPTION.put("build-property",
				"Add a build time property or properties file (syntax: [my-key=my-value|file:/path/to/my-conf.properties]\n\n"
				+ DESCRIPTION_SINCE_COMMUNITY_1_5_PROD_1_4);
		OPTION_NAMES_WITH_DESCRIPTION.put("config",
				"Add a runtime configuration from a Configmap, Secret or file (syntax: [configmap|secret|file]:name[/key],"
				+ " where name represents the local file path or the configmap/secret name and key optionally represents the configmap/secret key to be filtered)\n\n"
				+ DESCRIPTION_SINCE_COMMUNITY_1_5_PROD_1_4);
		OPTION_NAMES_WITH_DESCRIPTION.put(OPTION_NAME_DEPENDENCY, "An external library that should be included. E.g. for Maven dependencies \"dependency=mvn:org.my/app:1.0\"");
		OPTION_NAMES_WITH_DESCRIPTION.put("env", "Set an environment variable in the integration container. E.g \"env=MY_VAR=my-value\"");
		OPTION_NAMES_WITH_DESCRIPTION.put("label", "Add a label to the integration. E.g. \"label=my.company=hello\"");
		OPTION_NAMES_WITH_DESCRIPTION.put("name", "The integration name");
		OPTION_NAMES_WITH_DESCRIPTION.put(OPTION_NAME_OPEN_API, "Add an OpenAPI v2 spec (file path)");
		OPTION_NAMES_WITH_DESCRIPTION.put("profile", "Trait profile used for deployment");
		OPTION_NAMES_WITH_DESCRIPTION.put(OPTION_NAME_PROPERTY, "Add a camel property");
		OPTION_NAMES_WITH_DESCRIPTION.put(OPTION_NAME_PROPERTY_FILE,
				"Bind a property file to the integration. E.g. \"property-file=integration.properties\"\n\n"
				+ "Deprecated since Community 1.5 and Red Hat productized 1.4. Replaced by property=file:integration.properties");
		OPTION_NAMES_WITH_DESCRIPTION.put(OPTION_NAME_RESOURCE, "Add a resource");
		OPTION_NAMES_WITH_DESCRIPTION.put(OPTION_NAME_TRAIT, "Configure a trait. E.g. \"trait=service.enabled=false\"");
		COMPLETION_ITEMS = OPTION_NAMES_WITH_DESCRIPTION.entrySet().stream()
				.map(options -> createCompletionItem(options.getKey(), options.getValue()))
				.collect(Collectors.toList());
	}

	private static CompletionItem createCompletionItem(String label, String documentation) {
		CompletionItem completionItem = new CompletionItem(label);
		completionItem.setDocumentation(documentation);
		if (OPTION_NAME_PROPERTY_FILE.equals(label)) {
			completionItem.setTags(Collections.singletonList(CompletionItemTag.Deprecated));
		}
		return completionItem;
	}
	
	public static List<CompletionItem> getCompletionItems() {
		return COMPLETION_ITEMS;
	}
	
	public static List<CompletionItem> getCompletionItems(String filter) {
		return getCompletionItems().stream()
				.filter(FilterPredicateUtils.matchesCompletionFilter(filter))
				.collect(Collectors.toList());
	}

	public static String getDescription(String optionName) {
		return OPTION_NAMES_WITH_DESCRIPTION.get(optionName);
	}
	
}
