import './index.css';
import Header from './Header';
import Carousel from './Carousel';
import Products from "./Products";
import { useEffect, useState } from 'react';

const Navigation = () => {
    const [currentSlide, setCurrentSlide] = useState(0);
    const slides = [
        {
            id: 1,
            title: "First Slide",
            link: process.env.PUBLIC_URL + '/car-jew.jpg'
        },
        {
            id: 2,
            title: "Second Slide",
            link: process.env.PUBLIC_URL + '/set.jpg'
        },
    ];
    const slideNext = (e) => {
        setCurrentSlide((prev) => {
            return prev + 1 === slides.length ? 0 : currentSlide + 1;
        });
    };
    const slidePrev = (e) => {
        setCurrentSlide((prev) => {
            return prev === 0 ? slides.length - 1 : currentSlide - 1;
        });
    };
    useEffect(() => {
        const intervalId = setInterval(() => {
            setCurrentSlide((prev) => {
                return prev + 1 === slides.length ? 0 : prev + 1;
            });
        }, 4000);
        return () => {
            clearInterval(intervalId);
        };
    }, []);
    return (
        <>
        <header className="home-header">
            <Header />

            <Carousel
                image={slides[currentSlide]}
                slideNext={slideNext}
                slidePrev={slidePrev}
            />

        </header>
        <Products/>
        </>
    );
};

export default Navigation;