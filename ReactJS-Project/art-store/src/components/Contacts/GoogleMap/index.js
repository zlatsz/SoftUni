import * as React from "react";

import { useGoogleMaps } from "react-hook-google-maps";

const Map = () => {
  const { ref, map, google } = useGoogleMaps(
    process.env.REACT_APP_API_KEY,
    {
      center: { lat: 42.637, lng: 23.34},
      zoom: 16,
    },
  );
  if (map) {
    new google.maps.Marker({ position: { lat: 42.6371259, lng: 23.339921}, map });
  }

  return <div ref={ref} style={{ width: 500, height: 300 }} />;
};

export default Map;