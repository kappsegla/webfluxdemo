//package se.iths.martin.hello.elastic;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.springframework.stereotype.Service;
//import se.iths.martin.hello.models.Doc;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//public class ElasticSearch {
//    RestHighLevelClient client;
//
//    public ElasticSearch() {
//        client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("192.168.99.100", 9200, "http")));
//    }
//
//
//    public void close() {
//        try {
//            client.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void init() {
//
//        deleteIndex("docs");
//        createIndex("docs");
//
//
//        //Index some documents
//        indexDoc("docs", new Doc(UUID.randomUUID().toString(), "Martin", LocalDateTime.now() ,
//                "The road is the target."));
//        indexDoc("docs", new Doc(UUID.randomUUID().toString(), "Swanson", LocalDateTime.now(),
//                "When I eat, it is the food that is scared."));
//        indexDoc("docs", new Doc(UUID.randomUUID().toString(), "Swanson",LocalDateTime.now(),
//                "Just give me all the bacon and eggs you have."));
//        indexDoc("docs", new Doc(UUID.randomUUID().toString(), "Swanson",LocalDateTime.now(),
//                "The less I know about other people’s affairs, the happier I am."));
//    }
//
//    private void deleteIndex(String index) {
//        GetIndexRequest request = new GetIndexRequest(index);
//        try {
//            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
//            if (exists) {
//                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("docs");
//                var v = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
//                System.out.println("Deleted index " + index + ": " + v.isAcknowledged());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SneakyThrows
//    private void createIndex(String index) {
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 2)
//        );
//        String mapping = """
//                {
//                	"properties": {
//                   		"id": {
//                			"type": "keyword"
//                		},
//                		"username": {
//                			"type": "keyword"
//                		},
//                		"date_created": {
//                                    "type": "date",
//                                    "format": "yyyy-MM-dd'T'HH:mm:ss.SSS"
//                        },
//                		"data": {
//                			"type": "text"
//                		}
//                	}
//                }
//                                """;
//        //https://kb.objectrocket.com/elasticsearch/how-to-create-mapping-for-elasticsearch-with-kibana
//
//
//        request.mapping(mapping, XContentType.JSON);
//        CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//        System.out.println("Created index: " + indexResponse.index());
//    }
//
//
//    private void indexDoc(String index, Doc doc) {
//        //return Mono.create(sink -> {
//        IndexRequest request = new IndexRequest(index);
//        //request.id(doc.getId());
//        try {
//            request.source(new ObjectMapper().writeValueAsString(doc), XContentType.JSON);
//            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
//            System.out.println("Indexed : " + indexResponse.getIndex());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
////            client.indices().createAsync(request,
////                    RequestOptions.DEFAULT,
////                    new ActionListener<CreateIndexResponse>() {
////                        @Override
////                        public void onResponse(CreateIndexResponse createIndexResponse) {
////                            sink.success(createIndexResponse);
////                        }
////
////                        @Override
////                        public void onFailure(Exception e) {
////                            sink.error(e);
////                        }
////                    });
////        });
//    }
//
//
//}
