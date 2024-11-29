package lsit.Repositories;

import java.net.URI;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * Generic repository for managing items in an AWS S3 bucket.
 *
 * @param <T> The type of items managed by this repository.
 */
public abstract class GenericS3Repository<T> {
    final String BUCKET = "pizzeria_bucket";
    final String PREFIX;
    final Class<T> type;

    protected S3Client s3client;
    protected AwsCredentials awsCredentials;
    protected ObjectMapper objectMapper;

    /**
     * Constructs a new GenericS3Repository.
     *
     * @param bucketName The name of the S3 bucket.
     * @param itemType   The class type of the items managed by this repository.
     */
    public GenericS3Repository(String prefix, Class<T> type) {
        this.PREFIX = "pizzeria/" + prefix + "/";
        this.type = type;
        
        Dotenv dotenv = Dotenv.load();

        String accessKey = dotenv.get("ACCESS_KEY");
        String secretKey = dotenv.get("SECRET_KEY");

        if (accessKey == null || secretKey == null) {
            throw new IllegalStateException("AWS credentials are not set in environment variables");
        }
        awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        
        s3client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create("https://storage.googleapis.com"))
            .region(Region.of("auto"))
            .build();
        
        objectMapper = new ObjectMapper();
    }

    /**
     * Adds an item to the S3 bucket.
     *
     * @param item The item to add.
     */
    public void add(T item) {
        try {
            // Assume the item has a setId method and getId method
            setItemId(item);

            String json = objectMapper.writeValueAsString(item);
            
            s3client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + getItemId(item).toString())
                .build(),
                RequestBody.fromString(json)
            );
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an item from the S3 bucket by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return The retrieved item, or null if not found.
     */
    public T get(UUID id) {
        try {
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + id.toString())
                .build()
            ).readAllBytes();

            return objectMapper.readValue(objectBytes, type);
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Removes an item from the S3 bucket by its ID.
     *
     * @param id The ID of the item to remove.
     */
    public void remove(UUID id) {
        s3client.deleteObject(DeleteObjectRequest.builder()
            .bucket(BUCKET)
            .key(PREFIX + id.toString())
            .build()
        );  
    }

    /**
     * Updates an existing item in the S3 bucket.
     *
     * @param item The item to update.
     */
    public void update(T item) {
        try {
            T existing = get(getItemId(item));
            if(existing == null) return;

            String json = objectMapper.writeValueAsString(item);
            s3client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + getItemId(item))
                .build(),
                RequestBody.fromString(json)
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Lists all items in the S3 bucket.
     *
     * @return A list of all items.
     */
    public List<T> list() {
        List<T> items = new ArrayList<>();
        List<S3Object> objects = s3client.listObjects(ListObjectsRequest.builder()
          .bucket(BUCKET)
          .prefix(PREFIX)
          .build()  
        ).contents();

        for(S3Object o : objects) {
            try {
                String key = o.key();
                if (key.length() > PREFIX.length()) {
                    String idString = key.substring(PREFIX.length());
                    UUID id = UUID.fromString(idString);
                    T item = get(id);
                    if (item != null) {
                        items.add(item);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error processing object: " + o.key());
                e.printStackTrace();
            }
        }

        return items;
    }

    // Abstract methods for setting and getting the item's ID
    protected abstract void setItemId(T item);
    protected abstract UUID getItemId(T item);
}