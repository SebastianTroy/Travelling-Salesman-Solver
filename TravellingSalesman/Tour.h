#ifndef TOUR_H
#define TOUR_H

#include <QObject>
#include <QRect>
#include <QPointF>

#include <vector>
#include <functional>
#include <random>

class Tour : public QObject {
    Q_OBJECT
public:
    explicit Tour(QRect area, QObject* parent = nullptr);

    void AddStop(QPointF stop);
    void AddRandomStops(unsigned count);
    void SetLooped(bool looped);
    void SetTour(std::vector<QPointF>&& newTour);
    void ScaleTo(QRect area);
    void Shuffle();
    void MoveStopUnder(QLineF translation, int radius);
    void RemoveStopsUnder(QPointF location, int radius);
    void CullStopsOutOfBounds();
    void Clear();

    // The position of one stop in the tour is randomised
    void RandomiseStopLocation();
    //The position of a number of stops in the tour is randomised
    void RandomiseStopsLocations();
    // Selects a random subsection of the tour and reverses the order of the stops
    void ReverseSection();
    // Moves a section of the tour to the start or the end
    void MoveSection();
    // Swaps the position of two stops in the tour
    void SwapStops();

    std::vector<QPointF> GetTour() const;
    double GetDistance() const;
    bool IsLooped() const;
    void ForEachStop(const std::function<void(const QPointF& stop)>& action) const;

signals:
    void onTourUpdated();

private:
    inline static std::mt19937 entropy_ = std::mt19937();

    QRect currentArea_;
    std::vector<QPointF> tour_;
    bool looped_ = false;

    double GetDistance(const QPointF& a, const QPointF& b) const;
    int RandomTourIndex() const;
    std::pair<int, int> RandomUniqueTourIndices() const;

    static int RandomNumber(int min, int max);
    static std::pair<int, int> RandomUniqueNumbers(int min, int max);
};

#endif // TOUR_H
