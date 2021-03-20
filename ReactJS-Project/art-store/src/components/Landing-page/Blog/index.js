import './index.css';
import { Link } from 'react-router-dom';

const Blog = () => {
    return (
        <section className="landing-blog">
            <div className="landing-blog-title">
                <h4>ЕТЕРИЧНИ МАСЛА<span>БЛОГ</span></h4>
                <Link to="/login" className='landing-blog-link'>ОТИДИ ТАМ {'>'}</Link>
            </div>
            <article className='landing-blog-wrapper'>
                <div className="landing-about-blog">
                    <h3>ЕТЕРИЧНИТЕ МАСЛА И ТЯХНОТО ВЛИЯНИЕ ЗА ЖИВОТ БЕЗ БОЛЕСТ</h3>
                    <p>On Tuesday, April 3, 2018 Luxury Portfolio and Mansion Global co-hosted
                    вЂњA Night in SohoвЂќ in one of ManhattanвЂ™s premier penthouses at One Vandam
                    listed by Stribling & Associates. The event was the perfect opportunity to
                    highlight the potential to entertain 100+ people in this, the last penthouse
                    available in the building. The three-story space highlighted the best of SoHo
                    including the incredible architecture and the high-end food and entertainment
                        found in the area.</p>
                    <button>Прочети повече...</button>
                </div>
                <img src={process.env.PUBLIC_URL + '/set.jpg'} alt="oil" />
            </article>
        </section>
    );
};

export default Blog;