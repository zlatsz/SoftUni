import { Link } from 'react-router-dom';
import "./index.css"

const Post = ({
    id,
    name,
    imageURL,
}) => {

    return (

        <li className="blog-big_article">
            <section className="blog-article">
                <div className="blog-image">
                <Link to={`/blog/${id}`}  ><img className="blog-article_image" src={imageURL} alt="post" /> </Link> 
                </div>
                <div className="blog-article_text">
                    <h3>{name}</h3>
                </div>
            </section>
        </li>

    );

};

export default Post;