import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_CONFIG } from "../../config/api.config";
import { PostDTO } from "../../models/post.dto";
import { Observable } from "rxjs/Rx";


@Injectable()
export class DocumentationService {

    constructor(public http: HttpClient) {
    }
    
    findDocumentation() : Observable<PostDTO>{
        return this.http.get<PostDTO>(`${API_CONFIG.wpURL}/pages/1310`);
    }
}