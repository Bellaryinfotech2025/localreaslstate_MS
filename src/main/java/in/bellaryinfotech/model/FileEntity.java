package in.bellaryinfotech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_media")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🖼️ Image fields
    private String name;          // image name
    private String type;          // image MIME type
    private String imageUrl;      // image file URL (not binary data)

    // 🎥 Video fields
    private String videoName;
    private String videoType;
    private String videoUrl;

    // 🏡 Property details
    private String title;
    private String location;
    private String area;
    private String areaInCents;
    private String price;
    private String features;

    public FileEntity() {}

    public FileEntity(String name, String type, String imageUrl,
                      String videoName, String videoType, String videoUrl,
                      String title, String location, String area, String areaInCents,
                      String price, String features) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoUrl = videoUrl;
        this.title = title;
        this.location = location;
        this.area = area;
        this.areaInCents = areaInCents;
        this.price = price;
        this.features = features;
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getVideoName() { return videoName; }
    public void setVideoName(String videoName) { this.videoName = videoName; }

    public String getVideoType() { return videoType; }
    public void setVideoType(String videoType) { this.videoType = videoType; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getAreaInCents() { return areaInCents; }
    public void setAreaInCents(String areaInCents) { this.areaInCents = areaInCents; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", area='" + area + '\'' +
                ", areaInCents='" + areaInCents + '\'' +
                ", price='" + price + '\'' +
                ", features='" + features + '\'' +
                '}';
    }
}
