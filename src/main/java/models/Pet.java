package models;

import java.util.List;

/** Json Structure example
 *                   {
 *                     "id": _ID_,
 *                     "category": {
 *                       "id": 1,
 *                       "name": "string"
 *                     },
 *                     "name": "luna",
 *                     "photoUrls": [
 *                       "string"
 *                     ],
 *                     "tags": [
 *                       {
 *                         "id": 0,
 *                         "name": "string"
 *                       }
 *                     ],
 *                     "status": "available"
 *                   }
 */
public class Pet {

    private int id;
    private String name;
    private String status;
    private List<String> photoUrls;
    private List<PetTag> tags;
    private PetCategory category;

    public Pet() {}

    public Pet(int id, String name, String status, List<String> photoUrls, List<PetTag> tags, PetCategory category) {
        setId(id);
        setName(name);
        setStatus(status);
        setPhotoUrls(photoUrls);
        setTags(tags);
        setCategory(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<PetTag> getTags() {
        return tags;
    }

    public void setTags(List<PetTag> tags) {
        this.tags = tags;
    }

    public PetCategory getCategory() {
        return category;
    }

    public void setCategory(PetCategory category) {
        this.category = category;
    }
}
