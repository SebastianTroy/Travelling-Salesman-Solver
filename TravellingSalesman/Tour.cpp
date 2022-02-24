#include "Tour.h"

#include <QLine>

Tour::Tour(QRect area, QObject* parent)
    : QObject(parent)
    , currentArea_(area)
{
}

void Tour::AddStop(QPointF stop)
{
    tour_.push_back(stop);
    emit onTourUpdated();
}

void Tour::AddRandomStops(unsigned count)
{
    for (unsigned i = 0; i < count; ++i) {
        AddStop(QPointF(RandomNumber(0, currentArea_.width()), RandomNumber(0, currentArea_.height())));
    }
}

void Tour::SetLooped(bool looped)
{
    looped_ = looped;
    emit onTourUpdated();
}

void Tour::SetTour(std::vector<QPointF>&& newTour)
{
    tour_ = std::move(newTour);
}

void Tour::ScaleTo(QRect area)
{
    double xScale = static_cast<double>(area.width()) / currentArea_.width();
    double yScale = static_cast<double>(area.height()) / currentArea_.height();
    for (QPointF& stop : tour_) {
        stop.rx() *= xScale;
        stop.ry() *= yScale;
    }
    currentArea_ = area;
}

void Tour::Shuffle()
{
    std::random_shuffle(std::begin(tour_), std::end(tour_));
    emit onTourUpdated();
}

void Tour::MoveStopUnder(QLineF translation, int radius)
{
    for (QPointF& stop : tour_) {
        if (GetDistance(stop, translation.p1()) <= radius) {
            stop += (translation.p2() - translation.p1());
            emit onTourUpdated();
            return;
        }
    }
}

void Tour::RemoveStopsUnder(QPointF location, int radius)
{
    std::erase_if(tour_, [&](QPointF stop)
    {
        return GetDistance(stop, location) <= radius;
    });
    emit onTourUpdated();
}

void Tour::CullStopsOutOfBounds()
{
    std::erase_if(tour_, [&](QPointF stop)
    {
        return !currentArea_.contains(stop.toPoint());
    });
    emit onTourUpdated();
}

void Tour::Clear()
{
    tour_.clear();
    emit onTourUpdated();
}

void Tour::RandomiseStopLocation()
{
    if (tour_.size() > 1) {
        const auto& [ indexA, indexB ] = RandomUniqueTourIndices();{}
        QPointF copy = tour_[indexA];
        auto iter = std::begin(tour_);
        std::advance(iter, indexA);
        tour_.erase(iter);
        iter = std::begin(tour_);
        std::advance(iter, indexB);
        tour_.insert(iter, copy);
    }
}

void Tour::RandomiseStopsLocations()
{
    if (tour_.size() > 1) {
        unsigned count = RandomNumber(1, tour_.size());
        for (unsigned i = 0; i < count; ++i) {
            RandomiseStopLocation();
        }
    }
}

void Tour::ReverseSection()
{
    if (tour_.size() > 1) {
        auto [ start, end ] = RandomUniqueTourIndices();{}
        if (start > end) {
            std::swap(start, end);
        }
        while (end - start > 0) {
            std::swap(tour_[start], tour_[end]);
            ++start;
            --end;
        }
    }
}

void Tour::MoveSection()
{
    if (tour_.size() > 1) {
        auto [ start, end ] = RandomUniqueTourIndices();{}
        if (start > end) {
            std::swap(start, end);
        }
        std::vector<QPointF> section;
        std::copy(std::cbegin(tour_) + start, std::cbegin(tour_) + end, std::back_inserter(section));
        tour_.erase(std::begin(tour_) + start, std::begin(tour_) + end);
        std::move(std::cbegin(section), std::cend(section), std::back_inserter(tour_));
    }
}

void Tour::SwapStops()
{
    if (tour_.size() > 1) {
        const auto& [ indexA, indexB ] = RandomUniqueTourIndices();{}
        QPointF& a = tour_[indexA];
        QPointF& b = tour_[indexB];
        std::swap(a, b);
    }
}

std::vector<QPointF> Tour::GetTour() const
{
    return tour_;
}

double Tour::GetDistance() const
{
    double distance = 0;
    QPointF lastStop = tour_.empty() ? QPointF() : tour_.front();
    ForEachStop([&](const QPointF& stop)
    {
        distance += GetDistance(lastStop, stop);
        lastStop = stop;
    });

    if (looped_ && tour_.size() >= 2) {
        distance += GetDistance(tour_.front(), tour_.back());
    }

    return distance;
}

bool Tour::IsLooped() const
{
    return looped_;
}

void Tour::ForEachStop(const std::function<void (const QPointF&)>& action) const
{
    for (const auto& stop : tour_) {
        std::invoke(action, stop);
    }
}

double Tour::GetDistance(const QPointF& a, const QPointF& b) const
{
    return std::sqrt(std::pow(a.x() - b.x(), 2) + std::pow(a.y() - b.y(), 2));
}

int Tour::RandomTourIndex() const
{
    return RandomNumber(0, tour_.size() - 1);
}

std::pair<int, int> Tour::RandomUniqueTourIndices() const
{
    return RandomUniqueNumbers(0, tour_.size() - 1);
}

int Tour::RandomNumber(int min, int max)
{
    std::uniform_int_distribution<int> distribution(min, max);
    return distribution(entropy_);
}

std::pair<int, int> Tour::RandomUniqueNumbers(int min, int max)
{
    size_t a = RandomNumber(min, max);
    size_t b = RandomNumber(min, max);
    while (a == b) {
        b = RandomNumber(min, max);
    }
    return std::make_pair(a, b);
}
