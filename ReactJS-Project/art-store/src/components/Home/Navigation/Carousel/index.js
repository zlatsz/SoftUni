import "./index.css"

const Carousel = (props) => {

    return (
        <section className="home-header-carousel">
            <img className="home-header-carousel-img" src={props.image.link} alt="Slider_image" />
            <article className="home-header-carousel-btn-wrapper">
                <button onClick={props.slidePrev}>
                <i class="fas fa-caret-left fa-2x"></i>
                </button>
                <button onClick={props.slideNext}>
                <i class="fas fa-caret-right fa-2x"></i>
                </button>

            </article>
        </section>
    );
};

export default Carousel;