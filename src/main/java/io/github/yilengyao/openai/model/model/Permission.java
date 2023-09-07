package io.github.yilengyao.openai.model.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Permission {

  public String id;
  public String object;
  public long created;
  @JsonProperty("allow_create_engine")
  public boolean allowCreateEngine;
  @JsonProperty("allow_sampling")
  public boolean allowSampling;
  @JsonProperty("allow_logprobs")
  public boolean allowLogProbs;
  @JsonProperty("allow_search_indices")
  public boolean allowSearchIndices;
  @JsonProperty("allow_view")
  public boolean allowView;
  @JsonProperty("allow_fine_tuning")
  public boolean allowFineTuning;
  public String organization;
  public String group;
  @JsonProperty("is_blocking")
  public boolean isBlocking;

  public io.github.yilengyao.openai.graphql.generated.types.Permission toGraphQl() {
    return io.github.yilengyao.openai.graphql.generated.types.Permission.newBuilder()
        .id(this.getId())
        .object(this.getObject())
        .created(this.getCreated())
        .allowCreateEngine(this.isAllowCreateEngine())
        .allowSampling(this.isAllowSampling())
        .allowLogProbs(this.isAllowLogProbs())
        .allowSearchIndices(this.isAllowSearchIndices())
        .allowView(this.isAllowView())
        .allowFineTuning(this.isAllowFineTuning())
        .organization(this.getOrganization())
        .group(this.getGroup())
        .isBlocking(this.isBlocking())
        .build();
  }

}
