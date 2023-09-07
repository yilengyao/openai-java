[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.yilengyao/openai/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.yilengyao/openai)

You can reach me at <a href="https://www.linkedin.com/in/yi-leng-yao-84451275/">
    <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn" width="90">
</a>
# openai-java
This is a Java library that contains
- client: Spring WebClient that allows your Java application to call OpenAI's api.
- GraphQL Schema and DGS objects for exposing OpenAI's endpoint via GraphQL

# Requirements
- OPENAI_API_KEY

# Setting Up Your OpenAI API Key
- Create an OpenAI Account: If you don’t have an OpenAI account, sign up here.
- Manage OpenAI Credits: If your OpenAI credits are depleted or expired, set up your billing methods here.
- Set Usage Limits: To prevent unexpected charges, configure your Hard and Soft usage limits here.
- Generate Your API Key: Create your API key here, save this key securely.
- Set Up the API Key as an Environment Variable: Use the following command to set your API key as an environment variable:
```
export OPENAI_API_KEY="your api-key"
```
Your Spring Boot application will retrieve the OpenAI Api key from the environment variable, and use the api key to call OpenAI’s API.

# Setting up your Spring Boot Project
## Adding Dependencies
You will need to add the following dependencies to your pom.xml

```
<!-- openai client dependency -->
<dependency>
    <groupId>io.github.yilengyao</groupId>
    <artifactId>openai</artifactId>
    <version>{version 1.0.0 and above}</version>
</dependency>

<!-- graphql dependency -->
<dependency>
    <groupId>com.netflix.graphql.dgs</groupId>
    <artifactId>graphql-dgs-spring-boot-starter</artifactId>
    <version>7.3.6</version>
</dependency>

<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java-extended-scalars</artifactId>
    <version>21.0</version>
 </dependency>

 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
 </dependency>
 ```
### Compatibility Note:
- If your spring-boot-starter-parent version is 3.x.x or later, you must use graphql-dgs-spring-boot-starter version 6.x.x or later. This setup also requires JDK 17.
- For spring-boot-starter-parent version 2.7.x, opt for graphql-dgs-spring-boot-starter version 5.5.x.
- If your application runs on Spring Boot 2.6, you should use graphql-dgs-spring-boot-starter version 5.4.x or earlier.

## Setting Up GraphQL Datafetcher in Your Spring Boot Application
- Create the folder graphql in your project and inside the graphql folder create a file named `ApplicationSpecificSpringComponentScanMarker.java`. This interface acts as a marker for Spring component scanning:
```
public interface ApplicationSpecificSpringComponentScanMarker {
}
```


- Within the graphql folder, add another file named OpenAiDataFetcher.java. This file will contain the actual data fetching logic, which will interface with the OpenAI client:
```
import com.netflix.graphql.dgs.DgsComponent;
import io.github.yilengyao.openai.client.OpenAiClient;

@DgsComponent
public class OpenAiDataFetcher {

  private final OpenAiClient openAiClient;

  @Autowired
  public OpenAiDataFetcher(OpenAiClient openAiClient) {
    this.openAiClient = openAiClient;
  }
```
<br>

- Configuring Your Application to Scan Datafetcher Class and OpenAI-Java Package: In you main class
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
  scanBasePackageClasses = {
    io.github.yilengyao.openai.configuration.ApplicationSpecificSpringComponentScanMarker.class,
    <your package>.graphql.ApplicationSpecificSpringComponent
  }
)
public class <Main-Class-Name> {
    public static void main(String[] args) {
        SpringApplication.run(<Main-Class-Name>.class, args);
    }
};
```

# Integrating With OpenAI’s Endpoints
## Audio
### [Create Translation](https://platform.openai.com/docs/api-reference/audio/createTranscription)
OpenAI provides an endpoint that can translate audio content of another language into English. Here’s how to integrate this functionality into your Spring Boot application using GraphQL:

Update the `OpenAiDataFetcher` Class: In the `OpenAiDataFetcher` class, add the following method:
```
@DgsMutation
  public TextResponse createTranslation(
      @InputArgument("file") MultipartFile file,
      @InputArgument("translationInput") TranslationInput translationInput) throws IOException {
    return openAiClient.createTranslation(TranslationPayload.fromGraphQl(file, translationInput))
        .toGraphQl();
  }
```
<br>

Update the file upload size limit by adding the following to the application.properties file.
```
spring.servlet.multipart.max-file-size=25MB
spring.servlet.multipart.max-request-size=25MB
```
`MultipartFile` is Spring framework’s representation of an unloaded file. By default maximum upload size is 1MB. Since OpenAI currently supports maximum file size upload of 25MB, we update the MultipartFile size limit to 25MB.

<br>
You can launch your Spring Boot by executing the following command in your terminal.

```
./mvnw spring-boot:run
```

### Now we can call the translation endpoint using GraphQL:

- Install the Altair GraphQL Chrome extension
Launch Altair UI.
- For the endpoint, input: http://localhost:8080/graphql.
- Ensure the request method is set to POST
- Use the following GraphQL mutation to initiate the translation:
```
mutation CreateTranslation($file: Upload!) {
  createTranslation(
    file: $file,
    translationInput: {
      model: "whisper-1",
      prompt: "translate to english"
    }
  ) {
    text
  }
}
```
<br>
Uploading the Audio File:

- Locate the ellipsis (...) next to Upload and click on it.
- Choose an audio file from your device. OpenAI only support formats: flac, mp3, mp4, mpeg, mpga, m4a, ogg, wav, or webm.

https://github-production-user-asset-6210df.s3.amazonaws.com/31053252/266438265-12598a15-1fd4-4ee1-a578-904ce44b2b8a.mp4

### Handling CSRF Prevention Header Error:
If you encounter a CSRF prevention header error in Altair

```
expecting a csrf prevention header but none was found, supported headers are [apollo-require-preflight, x-apollo-operation-name, graphql-require-preflight
```
follow these steps:

- In Altair’s Headers section, add a new header: apollo-require-preflight.
- Set its value to true.

### [Create Transcription](https://platform.openai.com/docs/api-reference/audio/createTranscription)
OpenAI provides an endpoint that can transcribe audio content into text based on the input language. Here’s how to integrate this functionality into your Spring Boot application using GraphQL:

- Update the `OpenAiDataFetcher` Class: In the `OpenAiDataFetcher` class, add the following method:
```
 @DgsMutation
  public TextResponse createTranscription(
      @InputArgument("file") MultipartFile file,
      @InputArgument("transcriptionInput") TranscriptionInput transcriptionInput) throws IOException {
    return openAiClient.createTranscription(TranscriptionPayload.fromGraphQl(file, transcriptionInput))
        .toGraphQl();
  }
```
You can launch your Spring Boot by executing the following command in your terminal.
```
./mvnw spring-boot:run
```
Now we can call the transcription endpoint using GraphQL:
- Launch Altair UI.
- For the endpoint, input: http://localhost:8080/graphql.
- Ensure the request method is set to POST
- Use the following GraphQL mutation to initiate the transcription:
```
mutation CreateTranscription($file: Upload!) {
  createTranscription(file: $file, 
    transcriptionInput: {
      model: "whisper-1",
      prompt: "Optional prompt text here",
      language: "en"           # Optional language code
  }) {
    text
  }
}
```
Uploading the Audio File:

- Locate the ellipsis (...) next to Upload and click on it.
- Choose an audio file from your device. OpenAI only support formats: flac, mp3, mp4, mpeg, mpga, m4a, ogg, wav, or webm.

## Models
OpenAI offers an endpoint to list and describe the various models available. Here’s how to integrate this functionality into your Spring Boot application using GraphQL:

Update the `OpenAiDataFetcher` Class: In the `OpenAiDataFetcher` class, add the following method:
```
 @DgsQuery
  public ModelsOutput models(
      @InputArgument("id") Optional<String> id) {
    return id.isPresent()
        ? openAiClient.models(id.get()).toGraphQl()
        : openAiClient.models().toGraphQl();
  }
```
You can launch your Spring Boot by executing the following command in your terminal.

Then go to http://localhost:8080/graphiql

### [Listing Modes using GraphiQL](https://platform.openai.com/docs/api-reference/models/list)
- Accessing the GraphQL UI: Navigate to http://localhost:8080/graphiql to access the GraphQL interface.
- Listing Models: To view the available models use the following GraphQL query:
```
query AllModels {
  models {
    ... on OpenAiResponse {
      data {
        id
        object
        created
        ownedBy
        permission {
          id
          object
          created
          allowCreateEngine
          allowSampling
          allowLogProbs
          allowSearchIndices
          allowView
          allowFineTuning
          organization
          group
          isBlocking
        }
        root
        parent
      }
      object
    }
  }
}
```

### [Retrieving a Specific Model](https://platform.openai.com/docs/api-reference/models/retrieve)
Retrieves a model instance, providing basic information about the model such as the owner and permission.

- Listing Models: To view the available models use the following GraphQL query
```
query Model {
  models(id: "babbage") {
    __typename
    ... on Model {
      id
      object
     created
      ownedBy
      permission {
        id
        object
        created
        allowCreateEngine
        allowSampling
        allowLogProbs
        allowSearchIndices
        allowView
        allowFineTuning
        organization
        group
        isBlocking
      }
      root
      parent
    }
  }
}
```

## [Completions](https://platform.openai.com/docs/api-reference/completions/create)
OpenAI’s Completions endpoint provides responses based on given text prompts. Here’s how to integrate this functionality into your Spring Boot application using GraphQL:

- Update the `OpenAiDataFetcher` Class: In the `OpenAiDataFetcher` class, add the following method:
```
@DgsMutation
  public CompletionOutput completion(
      @InputArgument("completionInput") CompletionInput completionInput) {
    return openAiClient
        .completion(CompletionPayload.fromGraphQl(completionInput))
        .toGraphQl();
  }
```
<br>
After integrating the necessary dependencies, start your Spring Boot application with:

```
./mvnw spring-boot:run
```
Then go to http://localhost:8080/graphiql

Using GraphQL to Query:

- Accessing the GraphQL Interface: Open your browser and navigate to http://localhost:8080/graphiql.
- Generate a Completion with the following GraphQL mutation:
```
mutation Completion {
  completion(completionInput: {
    model: "text-davinci-003",
    prompt: "Why are cats so cute?",
    max_tokens: 73,
    temperature: 2,
    top_p: 1,
    n: 4,
    stream: false,
    stop: "\n",
  }) {
    id
    object
    created
    model
    choices {
      text
      index
      logprobs {
        tokens
        token_lobprobs
        top_logprobs {
          key
          value
        }
        text_offset
      }
      finish_reason
    }
    usage {
      prompt_tokens
      completion_tokens
      total_tokens
    }
  }
}
```

## [Chat](https://platform.openai.com/docs/api-reference/chat/create)
OpenAI’s Chat endpoint provides dynamic responses based on text prompts. This can simulate a back-and-forth conversation with the model. Here’s how to incorporate this into your Spring Boot application:

- Updating the `OpenAiDataFetcher` Class: Integrate the Chat endpoint to your `OpenAiDataFetcher` class:
```
@DgsMutation
  public ChatCompletionResult chatCompletion(
      @InputArgument("chatInput") ChatCompletionInput chatInput) {
    if (chatInput.getStream() != null && chatInput.getStream()) {
      return openAiClient
          .streamChatCompletion(ChatCompletionPayload.fromGraphQl(chatInput))
      .next()
      .map(ChatCompletionChunk::toGraphQl)
      .block();
    } else {
      return openAiClient
          .createChatCompletion(ChatCompletionPayload.fromGraphQl(chatInput))
          .toGraphQl();

    }
  }
```
<br>
- Launching the Application: Once you’ve appended the required dependencies, fire up your Spring Boot application:
```
./mvnw spring-boot:run
```
Then go to http://localhost:8080/graphiql

Querying with GraphQL:

- Accessing the GraphQL Interface: Launch http://localhost:8080/graphiql in your browser.
- Simulating a Chat: To create a chat completion using your parameters, input the following GraphQL mutation:
```
  mutation ChatCompletion {
  chatCompletion(chatInput: {
    model: "gpt-3.5-turbo",
    messages: [
      {
        role: "system",
        content: "Who is the greatest woccer player."
      },
      {
        role: "user",
        content: "How bad is Harry Maguire?"
      }
    ],
    stream: false
  }) {
    ... on ChatCompletion {
      id
      object
      create
      model
      choices {
        index
        message {
          role
          content
          function_call {
            name
            arguments
          }
        }
      }
      usage {
        prompt_tokens
        completion_tokens
        total_tokens
      }
    }
    ... on ChatCompletionChunk {
      id
      object
      created
      model
      choices {
        index
        delta {
          role
          content
          function_call {
            name
            arguments
          }
        }
      }
    }
  }
}
```

## Generating and Editing Images
OpenAI offers endpoints that allow you to create images from textual prompts and edit existing images.

### [Create Image](https://platform.openai.com/docs/api-reference/images/create)
This endpoint allows you creates an image with a prompt.

Here’s how to incorporate this into your Spring Boot application:

- Updating the `OpenAiDataFetcher` Class: Integrate the Chat endpoint to your `OpenAiDataFetcher` class:
```
@DgsMutation
  public ImageResponse createImage(
      @InputArgument("createImageInput") CreateImageInput createImageInput) {
    return openAiClient
        .createImage(CreateImagePayload.fromGraphQl(createImageInput))
        .toGraphQl();
  }
```
<br>
Running the Application:

After updating dependencies, start your Spring Boot app:
```
./mvnw spring-boot:run
```

Navigate to http://localhost:8080/graphiql and run the following mutation to generate an image:
```
mutation CreateImage {
  createImage(createImageInput: {
    prompt: "Nuclear fusion viewed from an atomic scale",
    # responseFormat: B64_JSON,
    n: 5,
    size: X256
  }) {
    createdAt
    data {
      url
      b64Json
    }
  }
}
```
https://github-production-user-asset-6210df.s3.amazonaws.com/31053252/266438260-248b7ee8-e85e-44c0-b38b-a1f4c2ea4316.mp4

### [Edit Images](https://platform.openai.com/docs/api-reference/images/createEdit)
This endpoint allows you to edit an existing image and a prompt.

Here’s how to incorporate this into your Spring Boot application:

- Updating the `OpenAiDataFetcher` Class: Integrate the Chat endpoint to your `OpenAiDataFetcher` class:

```
To add the Images endpoint, in the OpenAiDataFetcher class add
  @DgsMutation
  public ImageResponse createImage(
      @InputArgument("createImageInput") CreateImageInput createImageInput) {
    return openAiClient
        .createImage(CreateImagePayload.fromGraphQl(createImageInput))
        .toGraphQl();
  }
```
Now we can call the edit image endpoint using GraphQL:

- Launch Altair UI.
- For the endpoint, input: http://localhost:8080/graphql.
- Ensure the request method is set to POST
- Use the following GraphQL mutation to edit an image:
```
mutation EditImage($image: Upload!) {
  editImage(
    image: $image,
    # mask: $mask,
    editImageInput: {
      prompt: "Turn into winter wonderland",
      n: 3,
      size: X512,
      # responseFormat: URL,
      # user: "user"
    }
  ) {
    createdAt,
    data {
      url
      b64Json
    }
  }
}
```
Uploading the Image File:

- Locate the ellipsis (...) next to Upload and click on it.
- Choose an image file from your device. The image must be in png format, square, and less than 4MB.

https://github.com/yilengyao/openai-java/assets/31053252/4c38f2c4-a79c-4f67-955d-18892b799b62


## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.